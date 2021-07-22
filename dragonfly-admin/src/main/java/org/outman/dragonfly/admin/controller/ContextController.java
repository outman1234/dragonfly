package org.outman.dragonfly.admin.controller;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.outman.dragonfly.admin.entity.Result;
import org.outman.dragonfly.admin.utils.LayerStream;
import org.outman.dragonfly.repository.mongo.cx.MongoDataRepository;
import org.outman.dragonfly.repository.mongo.entitys.MongoGobalContext;
import org.outman.dragonfly.repository.mongo.entitys.MongoTransactionUndoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ContextController
 * @Description TODO
 * @Author OutMan
 * @create 2021-06-01 12:26
 */
@RestController
public class ContextController {

    @Autowired
    MongoDataRepository mongoDataRepository;

    @GetMapping("/Context/getList")
    public Result getList(@RequestParam("pageNum") int pageNum,
                          @RequestParam("size") int size,
                          @RequestParam(value = "statue", required = false) String statue) {
        List list = mongoDataRepository.getByPage(pageNum, size, MongoGobalContext.class, statue);
        Long totalCount = mongoDataRepository.getCount(MongoGobalContext.class);
        Result result = Result.builder().data(list).code(0).count(totalCount).build();
        return result;
    }

    @GetMapping("/Context/getListByXid")
    public Result getListByXid(@RequestParam("xid") String xid) {
        MongoGobalContext mongoGobalContext = (MongoGobalContext) mongoDataRepository.getByOther(MongoGobalContext.class, xid);
        Result result = Result.builder().data(mongoGobalContext).code(0).build();
        return result;
    }

    @GetMapping("/Context/undoLog")
    public Object undoLogTree(@RequestParam("id") String id) {
        MongoGobalContext mongoGobalContext = (MongoGobalContext) mongoDataRepository.getByOther(MongoGobalContext.class, id);
        List<MongoTransactionUndoLog> list = mongoDataRepository.getList(MongoTransactionUndoLog.class, id);
        MongoTransactionUndoLog mongoTransactionUndoLog = new MongoTransactionUndoLog();
        mongoTransactionUndoLog.setXid(id);
        mongoTransactionUndoLog.setPid("0");
        mongoTransactionUndoLog.setServiceName(mongoGobalContext.getStartMethodName());
        mongoTransactionUndoLog.setPositive(mongoGobalContext.getPositive());
        mongoTransactionUndoLog.setChildren(new ArrayList<>());
        mongoTransactionUndoLog.setZid(mongoGobalContext.getTransmissionLevel());
        mongoTransactionUndoLog.setName(mongoGobalContext.getStartMethodName());

        list.forEach(m -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(m.getMethodName());
            if (m.getPositive().equals(true)) {
                stringBuilder.append(" -> ");
            } else {
                stringBuilder.append(" <- ");
            }

            if (m.getExecSuccess().equals(false)) {
                stringBuilder.append("false");
            }
            m.setName(stringBuilder.toString());
        });
        list.add(mongoTransactionUndoLog);
        return LayerStream.tree(list);
    }

    @GetMapping("/Context/compensation")
    public String compensation(@RequestParam("xid") String xid) throws IOException {
        MongoGobalContext mongoGobalContext = (MongoGobalContext) mongoDataRepository.getByOther(MongoGobalContext.class, xid);
        Connection.Response response = Jsoup.connect(mongoGobalContext.getAppNameAdress() + "/invoker?id=" + mongoGobalContext.getId().toString()).method(Connection.Method.GET).execute();
        System.out.println(response.body());
        return "ok";
    }

}
