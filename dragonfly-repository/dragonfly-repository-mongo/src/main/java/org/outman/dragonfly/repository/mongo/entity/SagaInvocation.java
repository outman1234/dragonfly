package org.outman.dragonfly.repository.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName SagaInvocation
 * @Description TODO
 * @Author OutMan
 * @create 2021-05-31 10:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SagaInvocation implements Serializable {

    @Getter
    private Class<?> targetClass;

    @Getter
    private String methodName;

    @Getter
    private Class<?>[] parameterTypes;

    @Getter
    private Object[] args;
}
