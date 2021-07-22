package org.outman.dragonfly.rpc.springcloud.fegin;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName FeignInterceptorConfig
 * @Description TODO
 * @Author OutMan
 * @create 2020-10-22 13:57
 */
@Configuration
public class FeignInterceptorConfig {

    /**
     * feign请求拦截器
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        System.out.println("@@feign11");
        return new FeignRequestInterceptor();
    }

}
