package org.outman.dragonfly.mongo.springcloud.springbootstarter.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.outman.dragonfly.common.constants.Consts;
import org.outman.dragonfly.common.utils.ServeltUtil;
import org.outman.dragonfly.mongo.springcloud.springbootstarter.handler.FollowerParticipantHandler;
import org.outman.dragonfly.mongo.springcloud.springbootstarter.handler.StartParticipantHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * @ClassName Saga
 * @Description TODO
 * @Author OutMan
 * @create 2021-04-14 10:47
 */
@Component
public class SagaInterceptor implements TransactionInterceptor {

    @Autowired
    StartParticipantHandler startParticipantHandler;

    @Autowired
    FollowerParticipantHandler followerParticipantHandler;

    @Override
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        return doWith(s -> Objects.isNull(s), pjp);
    }

    private Object doWith(Predicate<String> predicate, ProceedingJoinPoint pjp) throws Throwable {
        if (predicate.test(ServeltUtil.getHeader(Consts.DRAGONFLYTOKEN))) {
            return startParticipantHandler.exec(pjp);
        } else {
            return followerParticipantHandler.exec(pjp);
        }

    }

}
