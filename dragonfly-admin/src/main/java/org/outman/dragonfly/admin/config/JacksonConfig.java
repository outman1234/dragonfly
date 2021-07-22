package org.outman.dragonfly.admin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @ClassName JacksonConfig
 * @Description TODO
 * @Author OutMan
 * @create 2021-05-27 9:52
 */
@Configuration
public class JacksonConfig implements InitializingBean {


    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void afterPropertiesSet() {
        SimpleModule simpleModule = new SimpleModule();
        //指定mongodb ObjectId 序列化方式
        simpleModule.addSerializer(ObjectId.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
    }


}
