package org.outman.dragonfly.mongo.springcloud.springbootstarter.handler;

import io.vavr.Function2;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.outman.dragonfly.mongo.springcloud.springbootstarter.utils.SagaUtils;
import org.outman.dragonfly.repository.mongo.cx.DataRepository;
import org.outman.dragonfly.repository.mongo.entity.GobalContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.function.Supplier;

/**
 * @ClassName FollowerParticipantHandler
 * @Description TODO
 * @Author OutMan
 * @create 2021-04-14 11:02
 */
@Slf4j
@Component
public class FollowerParticipantHandler implements ParticipantHandler {

    @Autowired
    DataRepository dataRepository;

    @Override
    public Object exec(ProceedingJoinPoint joinPoint) throws Throwable {

        GobalContext gobalContext = SagaUtils.getRemoteContext();

        return sum.curried().apply(() -> SagaUtils.getRemoteContext())
                .apply((k1, k2) -> step2(gobalContext, joinPoint));

    }

    Function2<Supplier, Function2, Object> sum = (a, b) -> {
        return null;
    };

    public Object step2(GobalContext gobalContext, ProceedingJoinPoint joinPoint) {
        try {
            if (gobalContext.getPositive().equals(true)) {
                return joinPoint.proceed();
            } else {
                Method backMethod = SagaUtils.getBackMethod(joinPoint);
                return backMethod.invoke(joinPoint.getTarget());
            }
        } catch (Throwable throwable) {
            log.error(throwable.getMessage(), throwable);
            throw new RuntimeException(throwable);
        }
    }

}
