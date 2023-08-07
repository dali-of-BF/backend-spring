package com.backend.annotation;


import org.springframework.http.HttpMethod;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 * 
 * @author FPH
 *
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD ,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 排除请求，默认get请求不记录至日志
     */
    HttpMethod[] excludeMethodType() default {HttpMethod.GET};
}
