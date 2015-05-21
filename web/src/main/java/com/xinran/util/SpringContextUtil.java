package com.xinran.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author 高海军 帝奇 Apr 19, 2015 12:40:57 PM
 */
public class SpringContextUtil implements ApplicationContextAware, BeanFactoryPostProcessor {

    private ApplicationContext              appContext;
    private ConfigurableListableBeanFactory factory;

    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
        this.factory = factory;
    }

    public void setApplicationContext(ApplicationContext c) throws BeansException {
        this.appContext = c;
    }

    public Object getBean(String name) {
        return appContext.getBean(name);
    }

    public ApplicationContext getAppContext() {
        return appContext;
    }

    public void setAppContext(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    public ConfigurableListableBeanFactory getFactory() {
        return factory;
    }

    public void setFactory(ConfigurableListableBeanFactory factory) {
        this.factory = factory;
    }

}
