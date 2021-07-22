package org.outman.dragonfly.mongo.springcloud.springbootstarter.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.outman.dragonfly.annotation.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * @ClassName AbstractTransactionAspect
 * @Description TODO
 * @Author OutMan
 * @create 2021-04-14 10:35
 */
@Aspect
@Component
public class TransactionAspect {

    @Autowired
    private SagaInterceptor interceptor;

    @Autowired
    private SagaRpcInterceptor sagaRpcInterceptor;

    @Pointcut("@annotation(org.outman.dragonfly.annotation.Saga)||@annotation(org.outman.dragonfly.annotation.SagaRpc)")
    public void transactionInterceptor() {
    }

    @Around("transactionInterceptor()")
    public Object interceptMethod(final ProceedingJoinPoint pjp) throws Throwable {
        Annotation[] annotations = ((MethodSignature) pjp.getSignature()).getMethod().getAnnotations();
        if (Arrays.stream(annotations).anyMatch(a -> a instanceof Saga)) {
            return interceptor.interceptor(pjp);
        } else {
            return sagaRpcInterceptor.interceptor(pjp);
        }

    }

}
