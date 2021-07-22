package org.outman.dragonfly.mongo.springcloud.springbootstarter.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.outman.dragonfly.core.context.TransactionContext;
import org.outman.dragonfly.repository.mongo.cx.DataRepository;
import org.outman.dragonfly.repository.mongo.entity.GobalContext;
import org.outman.dragonfly.repository.mongo.entity.TransactionUndoLog;
import org.outman.dragonfly.repository.mongo.utils.DataRepositoryUtils;

import java.util.function.Supplier;

/**
 * @ClassName CostTime
 * @Description TODO
 * @Author OutMan
 * @create 2021-04-21 11:46
 */
@Slf4j
public class Delegate implements AutoCloseable ,JointPointProcess{

    private long start;

    private DataRepository dataRepository;

    private TransactionUndoLog transactionUndoLog;

    private Object id;

    public Delegate(DataRepository dataRepository, Supplier<TransactionUndoLog> supplier, Object id) {
        this.dataRepository = dataRepository;
        this.transactionUndoLog = supplier.get();
        this.id = id;

        this.start = System.currentTimeMillis();
    }

    @Override
    public Object proceed(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return retrySevice(pjp);
        } catch (Throwable throwable) {
            transactionUndoLog.setExecSuccess(false);
            DataRepositoryUtils.convertAndUpdate(dataRepository, transactionUndoLog, id);
            throw throwable;
        }
    }

    @Override
    public void close() throws Exception {
        GobalContext gobalContext = TransactionContext.CURRENT_LOCAL.get();
        gobalContext.setTransmissionLevel(TransactionContext.CURRENT_TMP_ZID.get());

        transactionUndoLog.setTimeConsuming(System.currentTimeMillis() - start);
        DataRepositoryUtils.convertAndUpdate(dataRepository, transactionUndoLog, id);
    }

    private Object retrySevice(ProceedingJoinPoint pjp) throws Throwable {
        int time = 3;
        for (int i = 0; i < time; i++) {
            try {
                return pjp.proceed();
            } catch (Throwable throwable) {
                if (i >= time - 1) {
                    throw throwable;
                }
            }
        }
        return null;
    }
}
