package org.outman.dragonfly.repository.mongo.cx;

public interface DataRepository<T> {

    <T> Object save(T t);

    <V> T get(Class<T> clazz, V id);

    <V> void delete(Class<T> clazz, V id);

    void update(T t);

    void updateByXid(T t,String xid);

    public Boolean exist(String xid,String serviceName);


}
