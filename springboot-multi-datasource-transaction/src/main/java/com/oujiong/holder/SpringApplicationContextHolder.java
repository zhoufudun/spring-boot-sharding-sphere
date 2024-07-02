package com.oujiong.holder;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class SpringApplicationContextHolder implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringApplicationContextHolder.applicationContext =applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(Class<T> clz) {
        return applicationContext.getBean(clz);
    }

    public static <T> T getBean(String beanName,Class<T> clz) {
        return applicationContext.getBean(beanName,clz);
    }

}

