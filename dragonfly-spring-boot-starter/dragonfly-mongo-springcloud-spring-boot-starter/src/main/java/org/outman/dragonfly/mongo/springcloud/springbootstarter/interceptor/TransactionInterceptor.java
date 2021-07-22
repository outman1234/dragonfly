package org.outman.dragonfly.mongo.springcloud.springbootstarter.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @ClassName TransactionInterceptor
 * @Description TODO
 * @Author OutMan
 * @create 2021-04-14 10:36
 */
public interface TransactionInterceptor {

    /**
     * interceptor handler aop.
     *
     * @param pjp point cut.
     * @return Object object
     * @throws Throwable Throwable
     */
    Object interceptor(ProceedingJoinPoint pjp) throws Throwable;
}
