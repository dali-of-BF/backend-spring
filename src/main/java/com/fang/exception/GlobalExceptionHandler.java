package com.fang.exception;

import com.fang.common.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity handlerHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e,HttpServletRequest request){
        return ResultUtil.error("请求方式异常",e.getMessage()+",请求路径为->"+request.getRequestURI());
    }

    /**
     * 拦截业务exception返回消息
     */
    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity handleBusinessExceptionHandler(BusinessException e) {
        return ResultUtil.error(e.getMessage(), e.getErrData());
    }

    /**
     * 拦截DTO校验错误，同时返回详细字段消息
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        log.warn("请求参数校验", ex);
        BindingResult result = ex.getBindingResult();
        List<String> fieldErrors = result.getFieldErrors().stream()
                .map(f -> new FieldErrorVO(f.getObjectName().replaceFirst("DTO$", ""), f.getField(), f.getDefaultMessage()))
                .collect(Collectors.toList()).stream().map(FieldErrorVO::getMessage).collect(Collectors.toList());

        String fieldMsg = String.join(",", fieldErrors);
        return ResultUtil.error(fieldMsg);
    }

    /**
     * 系统异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity handlerException(Exception e,HttpServletRequest request){
        return ResultUtil.error( "系统异常",e.getMessage()+",请求路径为->"+request.getRequestURI());
    }
}
