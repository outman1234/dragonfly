package org.outman.dragonfly.mongo.springcloud.springbootstarter.handler;

import io.vavr.Function2;
import io.vavr.Function4;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.outman.dragonfly.common.enums.TransactionStatue;
import org.outman.dragonfly.core.context.TransactionContext;
import org.outman.dragonfly.mongo.springcloud.springbootstarter.config.ServerConfig;
import org.outman.dragonfly.mongo.springcloud.springbootstarter.utils.SagaUtils;
import org.outman.dragonfly.repository.mongo.cx.DataRepository;
import org.outman.dragonfly.repository.mongo.entity.GobalContext;
import org.outman.dragonfly.repository.mongo.utils.DataRepositoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @ClassName StartParticipantHandler
 * @Description TODO
 * @Author OutMan
 * @create 2021-04-14 11:01
 */
@Slf4j
@Component
public class StartParticipantHandler implements ParticipantHandler {

    @Autowired
    DataRepository dataRepository;

    @Autowired
    ServerConfig serverConfig;


    @Override
    public Object exec(ProceedingJoinPoint joinPoint) {
        Method backMethod = SagaUtils.getBackMethod(joinPoint);

        return sum.curried().apply((k1, k2) -> doWithBackInvoker(backMethod, joinPoint))
                .apply((k1, k2, k3, k4) -> {
                    try {
                        return step2(backMethod, joinPoint);
                    } catch (Throwable throwable) {
                        throw new RuntimeException(throwable);
                    }
                });
    }

    public Object step2(Method backMethod, ProceedingJoinPoint joinPoint) throws Throwable {
        Object id = SagaUtils.saveInitContext(dataRepository, joinPoint, backMethod, serverConfig);
        try {
            return joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable throwable) {
            log.error(throwable.getMessage(), throwable);
            catchMethod(backMethod, joinPoint);
            throw throwable;
        } finally {
            finalMethod(id);
        }
    }

    private Function2<Function2<Object, Object, Boolean>, Function4, Object> sum = (a, b) -> {
        if (!a.apply(1, 2)) {
            return b.apply(1, 2, 3, 4);
        }
        return null;
    };


    private Boolean doWithBackInvoker(Method backMethod, ProceedingJoinPoint joinPoint) {
        GobalContext gobalContext = TransactionContext.CURRENT_LOCAL.get();
        if (Objects.nonNull(gobalContext) && gobalContext.getFromDataBase().equals(true)) {
            catchMethodWithBack(backMethod, joinPoint);
            TransactionContext.CURRENT_LOCAL.remove();
            return true;
        }
        return false;
    }

    private void catchMethodWithBack(Method backMethod, ProceedingJoinPoint joinPoint) {
        TransactionContext.CURRENT_LOCAL.get().setPositive(false);
        try {
            backMethod.invoke(joinPoint.getTarget(), joinPoint.getArgs());
            TransactionContext.get().setStatue(TransactionStatue.BACKWARDSUCCESS);
        } catch (Throwable throwable) {
            log.error(throwable.getMessage(), throwable);
            TransactionContext.get().setStatue(TransactionStatue.EXECERROR);
        } finally {
            if (TransactionContext.get().getStatue().equals(TransactionStatue.EXECUTING)) {
                TransactionContext.get().setStatue(TransactionStatue.EXECSUCCESS);
            }
            DataRepositoryUtils.convertAndUpdateByXid(dataRepository, TransactionContext.get());
        }
    }

    private void catchMethod(Method backMethod, ProceedingJoinPoint joinPoint) {
        TransactionContext.CURRENT_LOCAL.get().setPositive(false);
        try {
            backMethod.invoke(joinPoint.getTarget(), joinPoint.getArgs());
            TransactionContext.get().setStatue(TransactionStatue.BACKWARDSUCCESS);
        } catch (Throwable throwable) {
            log.error(throwable.getMessage(), throwable);
            TransactionContext.get().setStatue(TransactionStatue.EXECERROR);
        }
    }

    private void finalMethod(Object id) {
        if (TransactionContext.get().getStatue().equals(TransactionStatue.EXECUTING)) {
            TransactionContext.get().setStatue(TransactionStatue.EXECSUCCESS);
        }
        DataRepositoryUtils.convertAndUpdate(dataRepository, TransactionContext.get(), id);
    }

}
