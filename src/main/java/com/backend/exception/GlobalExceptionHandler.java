package com.backend.exception;

import com.backend.common.HttpStatus;
import com.backend.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author FPH
 * @since 2022年4月28日12:54:28
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 请求方式异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<Object> handlerHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e,HttpServletRequest request){
        return Result.error(e.getMessage(),"请求路径为->"+request.getRequestURI(),"请求方式异常",HttpStatus.ERROR,e.getMessage());
    }

    /**
     * 拦截业务exception返回消息
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Object> handlerBindException(HttpServletRequest request, BusinessException e) {
        log.error("API 地址：[{}]", request.getRequestURL());
        return Result.error(e.getStackCode(), e.getStackMsg(),HttpStatus.ERROR,e.getStackMsg());
    }

    /**
     * 拦截DTO校验错误，同时返回详细字段消息
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        log.warn("请求参数校验", ex);
        BindingResult result = ex.getBindingResult();
        List<String> fieldErrors = result.getFieldErrors().stream()
                .map(f -> new FieldErrorVO(f.getObjectName().replaceFirst("DTO$", ""), f.getField(), f.getDefaultMessage()))
                .collect(Collectors.toList()).stream().map(FieldErrorVO::getMessage).collect(Collectors.toList());

        String fieldMsg = String.join(",", fieldErrors);
        return Result.error(fieldMsg);
    }

    /**
     * 拦截MybatisPlus异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = MyBatisSystemException.class)
    public Result<String> handleMyBatisSystemException(MyBatisSystemException e){
        return Result.error(e.getMessage());
    }

    /**
     * 系统异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<Object> handlerException(RuntimeException e,HttpServletRequest request){
        log.error("系统异常！原因：{}",e.getMessage());
        return Result.error( e.getMessage(),e,"系统异常", HttpStatus.ERROR,"请求路径为->"+request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public Result<Object> handlerException(Exception e,HttpServletRequest request){
        log.error("系统异常！原因：{}",e.getMessage());
        return Result.error( e.getMessage(),e,"系统异常", HttpStatus.ERROR,"请求路径为->"+request.getRequestURI());
    }
}
