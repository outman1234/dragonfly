package org.outman.dragonfly.admin.utils;

import org.outman.dragonfly.repository.mongo.entitys.MongoTransactionUndoLog;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassName Stream流递归实现遍历树形结构
 * @Description TODO
 * @Author OutMan
 * @create 2021-04-02 15:40
 */
public class LayerStream {

    public static List<MongoTransactionUndoLog> tree(List<MongoTransactionUndoLog> menus) {

        //获取父节点
        List<MongoTransactionUndoLog> collect = menus.stream().filter(m -> m.getPid().equals("0")).map(
                (m) -> {
                    m.setChildren(getChildrens(m, menus));
                    return m;
                }
        ).collect(Collectors.toList());
        return collect;
    }


    private static List<MongoTransactionUndoLog> getChildrens(MongoTransactionUndoLog root, List<MongoTransactionUndoLog> all) {
        List<MongoTransactionUndoLog> children = all.stream().filter(m -> {
            return Objects.equals(m.getPid(), root.getZid());
        }).map(
                (m) -> {
                    m.setChildren(getChildrens(m, all));
                    return m;
                }
        ).collect(Collectors.toList());
        return children;
    }
}
