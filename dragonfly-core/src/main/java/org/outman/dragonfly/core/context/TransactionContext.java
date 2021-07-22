package org.outman.dragonfly.core.context;

import org.outman.dragonfly.repository.mongo.entity.GobalContext;

/**
 * @ClassName TransactionContext
 * @Description TODO
 * @Author OutMan
 * @create 2021-04-09 16:01
 */

public class TransactionContext{

    public static final ThreadLocal<GobalContext> CURRENT_LOCAL = new ThreadLocal<>();

    public static final ThreadLocal<String> CURRENT_TMP_ZID = new ThreadLocal<>();

    public static GobalContext get(){
        return CURRENT_LOCAL.get();
    }

}
