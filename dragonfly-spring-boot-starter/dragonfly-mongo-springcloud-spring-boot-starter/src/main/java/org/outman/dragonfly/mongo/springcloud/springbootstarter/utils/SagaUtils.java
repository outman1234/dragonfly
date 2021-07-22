package org.outman.dragonfly.mongo.springcloud.springbootstarter.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.outman.dragonfly.annotation.Saga;
import org.outman.dragonfly.common.constants.Consts;
import org.outman.dragonfly.common.enums.TransactionStatue;
import org.outman.dragonfly.common.enums.TransactionType;
import org.outman.dragonfly.common.utils.ServeltUtil;
import org.outman.dragonfly.core.context.TransactionContext;
import org.outman.dragonfly.mongo.springcloud.springbootstarter.config.ServerConfig;
import org.outman.dragonfly.repository.mongo.cx.DataRepository;
import org.outman.dragonfly.repository.mongo.entity.GobalContext;
import org.outman.dragonfly.repository.mongo.entity.TransactionUndoLog;
import org.outman.dragonfly.repository.mongo.utils.DataRepositoryUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName SagaUtils
 * @Description TODO
 * @Author OutMan
 * @create 2021-04-15 16:19
 */
@Slf4j
public class SagaUtils {

    public static final Map<String, Method> reflectCaches = new ConcurrentHashMap<>();

    public static Method getBackMethod(ProceedingJoinPoint joinPoint) {
        Saga annotation = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Saga.class);
        String backMethod = annotation.backMethod();
        String joinPointMethodName = ((MethodSignature) joinPoint.getSignature()).getMethod().getName();
        String key = joinPoint.getTarget().getClass().getName() + ":" + joinPointMethodName;

        if (reflectCaches.containsKey(key)) {
            return reflectCaches.get(key);
        }

        ReflectionUtils.doWithMethods(joinPoint.getTarget().getClass(), m -> {
            if (m.getName().equals(backMethod)) {
                reflectCaches.put(key, m);
            }
        });
        return reflectCaches.get(key);
    }

    public static GobalContext getRemoteContext() {
        String token = ServeltUtil.getHeader(Consts.DRAGONFLYTOKEN);

        GobalContext gobalContext = JSON.parseObject(token, GobalContext.class);
        gobalContext.setTransmissionLevel(gobalContext.getTransmissionLevel() + 1);
        log.debug("gobalContext:{}", gobalContext);
        TransactionContext.CURRENT_LOCAL.set(gobalContext);
        return gobalContext;
    }

    public static Object geneAndSaveUndoLog(DataRepository dataRepository, ProceedingJoinPoint pjp) {
        String serviceName = pjp.getTarget().getClass().getInterfaces()[0].getAnnotation(FeignClient.class).name();
        String zid = UUID.randomUUID().toString();
        GobalContext gobalContext = TransactionContext.CURRENT_LOCAL.get();
        TransactionUndoLog transactionUndoLog = new TransactionUndoLog();
        transactionUndoLog.setXid(gobalContext.getXid());
        transactionUndoLog.setPid(gobalContext.getTransmissionLevel());
        transactionUndoLog.setZid(zid);
        transactionUndoLog.setServiceName(serviceName);
        transactionUndoLog.setMethodName(((MethodSignature) pjp.getSignature()).getMethod().getName());
        transactionUndoLog.setPositive(gobalContext.getPositive());
        transactionUndoLog.setTimeConsuming(-1L);
        transactionUndoLog.setCreateTime(LocalDateTime.now());
        transactionUndoLog.setExecSuccess(true);

        TransactionContext.CURRENT_TMP_ZID.set(gobalContext.getTransmissionLevel());
        gobalContext.setTransmissionLevel(zid);
        return DataRepositoryUtils.convertAndSave(dataRepository, transactionUndoLog);
    }

    public static Boolean exist(DataRepository dataRepository, ProceedingJoinPoint pjp) {
        String serviceName = pjp.getTarget().getClass().getInterfaces()[0].getAnnotation(FeignClient.class).name();
        GobalContext gobalContext = TransactionContext.CURRENT_LOCAL.get();
        return dataRepository.exist(gobalContext.getXid(), serviceName);
    }


    public static Object saveInitContext(DataRepository dataRepository, ProceedingJoinPoint pjp, Method backMethod, ServerConfig serverConfig) {
        GobalContext gobalContext = new GobalContext();
        gobalContext.setXid(UUID.randomUUID().toString());
        gobalContext.setPositive(true);
        gobalContext.setTransmissionLevel(UUID.randomUUID().toString());
        gobalContext.setType(TransactionType.SAGA);
        gobalContext.setStatue(TransactionStatue.EXECUTING);
        gobalContext.setArgs(pjp.getArgs());
        gobalContext.setParameterTypes(backMethod.getParameterTypes());
        gobalContext.setCreateTime(LocalDateTime.now());
        gobalContext.setMethodName(backMethod.getName());
        gobalContext.setMethodClassName(pjp.getTarget().getClass().getName());
        gobalContext.setStartMethodName(((MethodSignature) pjp.getSignature()).getMethod().getName());
        gobalContext.setFromDataBase(false);
        gobalContext.setAppNameAdress(serverConfig.getUrl());
        gobalContext.setAppName(serverConfig.getAppName());
        TransactionContext.CURRENT_LOCAL.set(gobalContext);
        return DataRepositoryUtils.convertAndSave(dataRepository, gobalContext);
    }

}
