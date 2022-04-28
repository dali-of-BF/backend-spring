package com.fang.exception;

import com.fang.common.AjaxResult;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

/**
 * @author FPH
 * @since 2022年4月28日12:54:28
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 请求方式异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public AjaxResult handlerHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e,HttpServletRequest request){
        return AjaxResult.error("请求方式异常",e.getMessage()+",请求路径为->"+request.getRequestURI());
    }

    /**
     * 系统异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult handlerException(Exception e,HttpServletRequest request){
        return AjaxResult.error( "系统异常",e.getMessage()+",请求路径为->"+request.getRequestURI());
    }

    /**
     * 自定义验证异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(BindException.class)
    public AjaxResult handlerBindException(BindException e,HttpServletRequest request){
        String message=e.getAllErrors().get(0).getDefaultMessage();
        return AjaxResult.error("自定义验证异常",message+",请求路径为->"+request.getRequestURI());
    }

}
