package org.outman.dragonfly.mongo.springcloud.springbootstarter.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;

public interface JointPointProcess {

    /**
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    public Object proceed(ProceedingJoinPoint pjp) throws Throwable;
}
