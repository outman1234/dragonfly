package org.outman.dragonfly.mongo.springcloud.springbootstarter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @ClassName ServerConfig
 * @Description TODO
 * @Author OutMan
 * @create 2021-06-08 15:30
 */
@Component
public class ServerConfig implements ApplicationListener<WebServerInitializedEvent> {


    @Value("${spring.application.name}")
    String appName;

    private int serverPort;

    public String getUrl() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "http://" + address.getHostAddress() + ":" + this.serverPort;
    }

    public String getAppName() {
        return appName;
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.serverPort = event.getWebServer().getPort();
    }

}
