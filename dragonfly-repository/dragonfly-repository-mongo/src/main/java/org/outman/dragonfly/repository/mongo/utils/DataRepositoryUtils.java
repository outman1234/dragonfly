package org.outman.dragonfly.repository.mongo.utils;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.outman.dragonfly.repository.mongo.cx.DataRepository;
import org.outman.dragonfly.repository.mongo.entity.GobalContext;
import org.outman.dragonfly.repository.mongo.entity.TransactionUndoLog;
import org.outman.dragonfly.repository.mongo.entitys.MongoGobalContext;
import org.outman.dragonfly.repository.mongo.entitys.MongoTransactionUndoLog;

/**
 * @ClassName DataRepositoryUtils
 * @Description TODO
 * @Author OutMan
 * @create 2021-04-21 11:03
 */
public class DataRepositoryUtils {

    public static Object convertAndSave(DataRepository dataRepository, GobalContext gobalContext) {
        MongoGobalContext mongoGobalContext = BaseConverter.INSTANCE.toMongoContext(gobalContext);
        return dataRepository.save(mongoGobalContext);
    }

    public static Object convertAndSave(DataRepository dataRepository, TransactionUndoLog transactionUndoLog) {
        MongoTransactionUndoLog mongoTransactionUndoLog = BaseConverter.INSTANCE.toMongoUndoLog(transactionUndoLog);
        return dataRepository.save(mongoTransactionUndoLog);
    }

    public static void convertAndUpdate(DataRepository dataRepository, TransactionUndoLog transactionUndoLog, Object id) {
        Key key = (Key) id;
        MongoTransactionUndoLog mongoTransactionUndoLog = BaseConverter.INSTANCE.toMongoUndoLog(transactionUndoLog);
        mongoTransactionUndoLog.setId(new ObjectId(key.getId().toString()));
        dataRepository.update(mongoTransactionUndoLog);
    }

    public static void convertAndUpdate(DataRepository dataRepository, GobalContext gobalContext, Object id) {
        Key key = (Key) id;
        MongoGobalContext mongoGobalContext = BaseConverter.INSTANCE.toMongoContext(gobalContext);
        mongoGobalContext.setId(new ObjectId(key.getId().toString()));
        dataRepository.update(mongoGobalContext);
    }

    public static void convertAndUpdateByXid(DataRepository dataRepository, GobalContext gobalContext) {
        MongoGobalContext mongoGobalContext = BaseConverter.INSTANCE.toMongoContext(gobalContext);
        dataRepository.updateByXid(mongoGobalContext,mongoGobalContext.getXid());
    }
}
