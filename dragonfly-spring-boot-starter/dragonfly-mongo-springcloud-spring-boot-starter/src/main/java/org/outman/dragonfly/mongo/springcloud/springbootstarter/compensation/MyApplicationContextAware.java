package org.outman.dragonfly.mongo.springcloud.springbootstarter.compensation;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @ClassName ApplicationContextAware
 * @Description TODO
 * @Author OutMan
 * @create 2021-04-27 16:41
 */
@Component
public class MyApplicationContextAware implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        applicationContext = arg0;
    }

    public static Object getObject(String className) {
        Object object = null;
        try {
//            Class c = Class.forName("com.base.alibabanacosdiscoveryclientcommon.ClientServiceImpl");
//            //object = applicationContext.getBean(c);
//            object = applicationContext.getBean("clientServiceImpl");
            Class c = Class.forName(className);
            object = applicationContext.getBean(c);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }
}
