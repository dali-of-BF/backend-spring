package com.fang.common.utils;

public class Result<T> {
    /**
     * 业务逻辑成功
     */
    public static final Integer SUCCESS = 200;

    /**
     * 参数校验异常
     */
    public static final Integer ERROR = 400;

    /**
     * 认证失败
     */
    public static final Integer UNAUTHORIZED = 401;
    /**
     * 禁止访问
     */
    public static final Integer FORBIDDEN = 403;

    /**
     * 系统异常
     */
    public static final Integer INTERNAL_SERVER_ERROR = 500;


    /**
     * 业务异常
     */
    public static final Integer BUSINESS_ERROR = 412;

    /**
     * 错误码.
     */
    private Integer code;

    /**
     * 提示信息.
     */
    private String msg;

    /**
     * 具体的内容.
     */
    private T data;

    public Result(T data) {
        this.data = data;
    }

    public Result() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
