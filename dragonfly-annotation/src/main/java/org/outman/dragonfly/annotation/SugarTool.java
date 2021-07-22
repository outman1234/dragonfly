package org.outman.dragonfly.annotation;

import java.lang.annotation.*;

/**
 * @ClassName SugarTool
 * @Description TODO
 * @Author OutMan
 * @create 2020-10-21 16:37
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SugarTool {

    String remoteName() default "";

}
