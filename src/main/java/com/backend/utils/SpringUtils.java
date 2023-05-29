package com.backend.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * @author FPH
 */
@Component
public class SpringUtils implements BeanFactoryPostProcessor {

    /** Spring应用上下文环境 */
    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        SpringUtils.beanFactory = configurableListableBeanFactory;
    }
    /**
     * 获取对象
     *
     * @param name
     * @return Object 一个以所给名字注册的bean的实例
     * @throws org.springframework.beans.BeansException
     *
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) beanFactory.getBean(name);
    }

    /**
     * 获取类型为requiredType的对象
     *
     * @param clz
     * @return
     * @throws org.springframework.beans.BeansException
     *
     */
    public static <T> T getBean(Class<T> clz) throws BeansException {
        return beanFactory.getBean(clz);
    }

    /**
     * 通过切点获取注解信息
     * @param joinPoint
     * @return
     * @param <T>
     */
    public <T extends Annotation> T getAnnotation(JoinPoint joinPoint, @Nullable Class<T> annotationType){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        T annotation = AnnotationUtils.findAnnotation(signature.getMethod(), annotationType);
        if ((Objects.nonNull(annotation))){
            return annotation;
        }
        return AnnotationUtils.findAnnotation(signature.getDeclaringType(), annotationType);
    }


}
