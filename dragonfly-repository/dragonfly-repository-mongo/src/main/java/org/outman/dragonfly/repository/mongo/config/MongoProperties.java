package org.outman.dragonfly.repository.mongo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName TigerProperties
 * @Description TODO
 * @Author OutMan
 * @create 2021-04-22 17:39
 */
@Data
@Component
@ConfigurationProperties("dragonfly.repository.mongo")
public class MongoProperties {

    private Integer port;

    private String host;

}
