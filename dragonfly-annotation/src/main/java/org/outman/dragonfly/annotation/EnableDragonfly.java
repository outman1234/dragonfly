package org.outman.dragonfly.annotation;

import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

/**
 * @ClassName SugarTool
 * @Description TODO
 * @Author OutMan
 * @create 2020-10-21 16:37
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ComponentScan({"org.outman.dragonfly","com.base"})
@Documented
public @interface EnableDragonfly {

}
