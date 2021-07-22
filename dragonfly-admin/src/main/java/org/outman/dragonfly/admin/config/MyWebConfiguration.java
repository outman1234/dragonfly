package org.outman.dragonfly.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName MyWebConfiguration
 * @Description TODO
 * @Author OutMan
 * @create 2021-05-27 10:40
 */
@Configuration
public class MyWebConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowCredentials(true)
                        .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
                        .maxAge(3600);
            }
        };
    }
}
