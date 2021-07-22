package org.outman.dragonfly.mongo.springcloud.springbootstarter.compensation;

import org.bson.types.ObjectId;
import org.outman.dragonfly.core.context.TransactionContext;
import org.outman.dragonfly.repository.mongo.cx.MongoDataRepository;
import org.outman.dragonfly.repository.mongo.entitys.MongoGobalContext;
import org.outman.dragonfly.repository.mongo.utils.BaseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

/**
 * @ClassName InvokerController
 * @Description TODO
 * @Author OutMan
 * @create 2021-05-28 14:51
 */
@RestController
public class InvokerController {

    @Autowired
    MongoDataRepository mongoDataRepository;

    @GetMapping("/invoker")
    public String invoker(@RequestParam("id") String id) throws Exception {
        MongoGobalContext mongoGobalContext = (MongoGobalContext) mongoDataRepository.get(MongoGobalContext.class, new ObjectId(id));
        mongoGobalContext.setFromDataBase(true);
        TransactionContext.CURRENT_LOCAL.set(BaseConverter.INSTANCE.toGobalContext(mongoGobalContext));
        Object o = MyApplicationContextAware.getObject(mongoGobalContext.getMethodClassName());
        Class<?> clazz = Class.forName(mongoGobalContext.getMethodClassName());
        Method method = clazz.getMethod(mongoGobalContext.getStartMethodName(), mongoGobalContext.getParameterTypes());
        method.invoke(o, mongoGobalContext.getArgs());
        return "ok!!";
    }

}
