package org.outman.dragonfly.repository.mongo.entity;


import lombok.Data;
import org.outman.dragonfly.common.enums.TransactionStatue;
import org.outman.dragonfly.common.enums.TransactionType;

import java.time.LocalDateTime;

/**
 * @ClassName GobalContext
 * @Description TODO
 * @Author OutMan
 * @create 2020-10-22 11:51
 */
@Data
public class GobalContext {

    private String xid;

    private Boolean positive;

    private String TransmissionLevel;

    private TransactionType type;

    private TransactionStatue statue;

    private LocalDateTime createTime;

    private Object[] args;

    private String methodName;

    private String methodClassName;

    private Class<?>[] parameterTypes;

    private Boolean fromDataBase;

    private String startMethodName;

    private String appName;

    private String appNameAdress;
}
