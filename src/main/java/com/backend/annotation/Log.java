package com.backend.annotation;


import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 * 
 * @author FPH
 *
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

}
