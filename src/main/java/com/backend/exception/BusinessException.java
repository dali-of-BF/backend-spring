package com.backend.exception;

import com.backend.common.HttpStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 业务异常
 * @author FPH
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException implements Serializable {


    private static final long serialVersionUID = 8553984694456974512L;
    /**
     * 异常码
     */
    private String stackCode;

    /**
     * 异常提示信息
     */
    private String stackMsg;

    /**
     * 异常模块信息
     */
    private String module;

    /**
     * 异常携带的数据
     */
    private Object data;

    public BusinessException(String msg) {
        super(msg);
        this.stackCode= String.valueOf(HttpStatus.ERROR);
        this.stackMsg=msg;
    }

    public BusinessException(String stackCode, String stackMsg) {
        super(stackMsg);
        this.stackMsg = stackMsg;
        this.stackCode = stackCode;
    }


    public BusinessException(String stackCode, String stackMsg, Object data) {
        super(stackMsg);
        this.data = data;
        this.stackMsg = stackMsg;
        this.stackCode = stackCode;
    }
}

