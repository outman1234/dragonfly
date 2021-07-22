package org.outman.dragonfly.admin.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName InvokerServiceController
 * @Description TODO
 * @Author OutMan
 * @create 2021-06-04 14:57
 */
@RestController
public class InvokerServiceController {

    public static final ConcurrentHashMap<String, String> services = new ConcurrentHashMap<>();

    @PostMapping("/invokerService")
    public void register(@RequestParam("key") String key,@RequestParam("value") String value) {
        services.put(key, value);
    }

}
