package org.outman.dragonfly.repository.mongo.entitys;


import lombok.Data;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
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
@Entity
public class MongoGobalContext {

    @Id
    private ObjectId id;

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
