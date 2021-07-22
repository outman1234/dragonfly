package org.outman.dragonfly.repository.mongo.cx;


import lombok.Data;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.outman.dragonfly.repository.mongo.entity.TransactionUndoLog;
import org.outman.dragonfly.repository.mongo.entitys.MongoGobalContext;
import org.outman.dragonfly.repository.mongo.entitys.MongoTransactionUndoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

@Service
@Data
public class MongoDataRepository<T> implements DataRepository<T> {

    @Autowired
    private Datastore datastore;

    /**
     * 方法描述：save不报主键异常，要判断下.相同主键直接修改
     *
     * @return void
     */
    @Override
    public <T> Key<T> save(T t) {
        return datastore.save(t);
    }

    public <T> Key<T> save1(T t) {
        return datastore.save(t);
    }

    @Override
    public <V> T get(Class<T> clazz, V id) {
        return datastore.get(clazz, id);
    }

    @Override
    public <V> void delete(Class<T> clazz, V id) {
        datastore.delete(clazz, id);
    }

    @Override
    public void update(T t) {
        Class c = t.getClass();
        Field[] fs = c.getDeclaredFields();
        UpdateOperations updateOperation = datastore.createUpdateOperations(t.getClass());
        for (Field field : fs) {
            String fieldName = field.getName();
            Object value = getValueByPropertyName(t, fieldName);
            if (Objects.nonNull(value)) {
                updateOperation.set(fieldName, value);
            }

        }
        datastore.update(t, updateOperation);
    }

    @Override
    public void updateByXid(T t, String xid) {
        Query<MongoGobalContext> query = datastore.createQuery(MongoGobalContext.class).field("xid").equal(xid);
        UpdateOperations<MongoGobalContext> updateOperation = datastore.createUpdateOperations(MongoGobalContext.class).set("statue", "BACKWARDSUCCESS");
        UpdateResults updateResults = datastore.update(query, updateOperation);
    }

    /**
     * 方法描述：存在返回key 不存在返回null
     *
     * @return org.mongodb.morphia.Key<?>
     */
    public Key<?> exist(T t) {
        return datastore.exists(t);
    }

    @Override
    public Boolean exist(String xid, String serviceName) {
        Query<TransactionUndoLog> query = datastore.createQuery(TransactionUndoLog.class).field("xid").equal(xid).field("serviceName").equal(serviceName);
        return Objects.isNull(query.get());
    }

    /**
     * 方法getValueByPropertyName的功能描述：TODO
     *
     * @param obj
     * @param propertyName
     * @return java.lang.Object
     */
    public static Object getValueByPropertyName(Object obj, String propertyName) {
        String getMethodName = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        Class c = obj.getClass();
        try {
            Method m = c.getMethod(getMethodName);
            Object value = m.invoke(obj);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<T> getByPage(int pageNum, int size, Class clazz, String statue) {
        FindOptions findOptions = new FindOptions().skip((pageNum - 1) * size).limit(size);

        Query query = datastore.createQuery(clazz);
        query.order("-createTime");
        if (Objects.nonNull(statue)) {
            query.field("statue").equal(statue);
        }
        List<T> list = query.asList(findOptions);
        return list;
    }

    public Long getCount(Class clazz) {
        return datastore.getCount(clazz);
    }

    public List<T> getList(Class clazz, String id) {
        Query query = datastore.createQuery(MongoTransactionUndoLog.class).field("xid").equal(id);
        List<T> list = query.asList();
        return list;
    }

    public <V> T getByOther(Class<T> clazz, String xid) {
        Query query = datastore.createQuery(MongoGobalContext.class).field("xid").equal(xid);
        return (T) query.get();
    }
}
