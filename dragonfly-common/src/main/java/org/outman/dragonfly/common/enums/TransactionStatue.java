package org.outman.dragonfly.common.enums;

public enum TransactionStatue {


    /**
     *
     */
    EXECUTING(1,"执行中"),

    /**
     *
     */
    EXECSUCCESS(2,"执行成功"),

    /**
     *
     */
    BACKWARDSUCCESS(3,"逆向成功"),

    /**
     *
     */
    EXECERROR(4,"逆向失败");


    private Integer code;

    private String msg;

    TransactionStatue(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
