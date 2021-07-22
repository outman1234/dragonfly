package org.outman.dragonfly.repository.mongo.entitys;

import lombok.Data;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName ServiceLog
 * @Description TODO
 * @Author OutMan
 * @create 2020-12-04 16:05
 */
@Data
@Entity
public class MongoTransactionUndoLog {

    @Id
    private ObjectId id;

    private String xid;

    private String pid;

    private String serviceName;

    private Boolean positive;

    private Long timeConsuming;

    private LocalDateTime createTime;

    private transient List<MongoTransactionUndoLog> children;

    private String zid;

    private String methodName;

    private transient String name;

    private Boolean execSuccess;
}
