package org.outman.dragonfly.repository.mongo.utils;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.outman.dragonfly.repository.mongo.entity.GobalContext;
import org.outman.dragonfly.repository.mongo.entity.TransactionUndoLog;
import org.outman.dragonfly.repository.mongo.entitys.MongoGobalContext;
import org.outman.dragonfly.repository.mongo.entitys.MongoTransactionUndoLog;

/**
 * @ClassName PersonConverter
 * @Description TODO
 * @Author OutMan
 * @create 2021-04-21 10:39
 */
@Mapper
public interface BaseConverter {

    BaseConverter INSTANCE = Mappers.getMapper(BaseConverter.class);

    MongoGobalContext toMongoContext(GobalContext gobalContext);

    GobalContext toGobalContext(MongoGobalContext mongoGobalContext);

    MongoTransactionUndoLog toMongoUndoLog(TransactionUndoLog transactionUndoLog);

}
