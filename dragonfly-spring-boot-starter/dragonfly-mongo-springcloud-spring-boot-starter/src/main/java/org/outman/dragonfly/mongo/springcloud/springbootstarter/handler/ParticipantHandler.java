package org.outman.dragonfly.mongo.springcloud.springbootstarter.handler;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @ClassName ParticipantHandlerImpl
 * @Description TODO
 * @Author OutMan
 * @create 2021-04-14 10:59
 */
public interface ParticipantHandler {

    Object exec(ProceedingJoinPoint joinPoint) throws Throwable;
}
