package org.outman.dragonfly.mongo.springcloud.springbootstarter.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.outman.dragonfly.core.context.TransactionContext;
import org.outman.dragonfly.mongo.springcloud.springbootstarter.utils.SagaUtils;
import org.outman.dragonfly.repository.mongo.cx.DataRepository;
import org.outman.dragonfly.repository.mongo.entity.TransactionUndoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName Saga
 * @Description TODO
 * @Author OutMan
 * @create 2021-04-14 10:47
 */
@Slf4j
@Component
public class SagaRpcInterceptor implements TransactionInterceptor {

    @Autowired
    DataRepository dataRepository;

    @Override
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        if (TransactionContext.CURRENT_LOCAL.get().getPositive().equals(true)) {
            return positive(pjp);
        } else {
            return negetive(pjp);
        }
    }

    private Object positive(ProceedingJoinPoint pjp) throws Throwable {
        Object id = SagaUtils.geneAndSaveUndoLog(dataRepository, pjp);
        try (Delegate delegate = new Delegate(dataRepository, TransactionUndoLog::new, id)) {
            return delegate.proceed(pjp);
        } catch (Throwable throwable) {
            log.error(throwable.getMessage(), throwable);
            throw throwable;
        }
    }

    private Object negetive(ProceedingJoinPoint pjp) throws Throwable {

        if (!SagaUtils.exist(dataRepository, pjp)) {
            return null;
        } else {
            return positive(pjp);
        }

    }
}
