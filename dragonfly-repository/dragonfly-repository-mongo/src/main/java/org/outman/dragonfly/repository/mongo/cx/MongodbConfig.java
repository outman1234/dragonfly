package org.outman.dragonfly.repository.mongo.cx;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.outman.dragonfly.repository.mongo.config.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @ClassName MongodbConfig
 * @Description TODO
 * @Author OutMan
 * @create 2020-12-02 15:46
 */
@Configuration
public class MongodbConfig {

    @Bean
    public Datastore datastore(MongoProperties mongoProperties){
        System.out.println("@@mongo11");
        MongoClient mongo = new MongoClient(Arrays.asList(new ServerAddress(mongoProperties.getHost(), mongoProperties.getPort())));
        Morphia morphia = new Morphia();
        morphia.mapPackage("org.outman.dragonfly.repository.mongo.entity");
        Datastore datastore = morphia.createDatastore(mongo, "saga");
        datastore.ensureIndexes();
        return datastore;
    }

}
