package com.hx.blog_v2.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * SpringContext
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/9/2017 11:03 PM
 */
@Component
public final class SpringContext implements ApplicationContextAware {

    @Autowired
    private static ApplicationContext AC;

    public SpringContext() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AC = applicationContext;
    }

    /**
     * 获取 ApplicationContext
     *
     * @return org.springframework.context.ApplicationContext
     * @author Jerry.X.He
     * @date 6/9/2017 11:07 PM
     * @since 1.0
     */
    public static ApplicationContext getApplicationContext() {
        return AC;
    }

    /**
     * 获取 name 对应的 bean
     *
     * @param name name
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 6/9/2017 11:10 PM
     * @since 1.0
     */
    public static Object getBean(String name) throws BeansException {
        return AC.getBean(name);
    }

    /**
     * 获取 name, type 均匹配的 bean
     *
     * @param name name
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 6/9/2017 11:10 PM
     * @since 1.0
     */
    public static <T> T getBean(String name, Class<T> type) throws BeansException {
        return AC.getBean(name, type);
    }

    /**
     * 获取一个 type 对应的实例
     *
     * @param type type
     * @return T
     * @author Jerry.X.He
     * @date 6/9/2017 11:10 PM
     * @since 1.0
     */
    public static <T> T getBean(Class<T> type) throws BeansException {
        return AC.getBean(type);
    }

    /**
     * 获取name对应的实例, 并使用给定的参数列表初始化
     *
     * @param name name
     * @param args args
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 6/9/2017 11:11 PM
     * @since 1.0
     */
    public static Object getBean(String name, Object... args) throws BeansException {
        return AC.getBean(name, args);
    }

    /**
     * 获取 type 对应的实例, 并使用给定的参数列表初始化
     *
     * @param type type
     * @param args args
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 6/9/2017 11:11 PM
     * @since 1.0
     */
    public static <T> T getBean(Class<T> type, Object... args) throws BeansException {
        return AC.getBean(type, args);
    }

    /**
     * 判断 name 对应的 bean 是否存在
     *
     * @param name name
     * @return boolean
     * @author Jerry.X.He
     * @date 6/9/2017 11:13 PM
     * @since 1.0
     */
    public static boolean containsBean(String name) {
        return AC.containsBean(name);
    }

    /**
     * 判断给定的 name 对应的对象 scope 是否为 singleton
     *
     * @param name name
     * @return boolean
     * @author Jerry.X.He
     * @date 6/9/2017 11:13 PM
     * @since 1.0
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return AC.isSingleton(name);
    }

    /**
     * 判断给定的 name 对应的对象 scope 是否为 prototype
     *
     * @param name name
     * @return boolean
     * @author Jerry.X.He
     * @date 6/9/2017 11:13 PM
     * @since 1.0
     */
    public static boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        return AC.isPrototype(name);
    }

}
