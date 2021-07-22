package org.outman.dragonfly.repository.mongo.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName ServiceLog
 * @Description TODO
 * @Author OutMan
 * @create 2020-12-04 16:05
 */
@Data
public class TransactionUndoLog {

    private String xid;

    private String pid;

    private String serviceName;

    private Boolean positive;

    private Long timeConsuming;

    private LocalDateTime createTime;

    private String zid;

    private String methodName;

    private Boolean execSuccess;

}
