package org.outman.dragonfly.common.enums;

/**
 * @ClassName TransactionType
 * @Description TODO
 * @Author OutMan
 * @create 2021-04-07 9:14
 */
public enum TransactionType {

    /**
     *
     */
    SAGA(0),

    /**
     *
     */
    TCC(1);

    private Integer type;

    TransactionType(Integer type) {
        this.type = type;
    }
}
