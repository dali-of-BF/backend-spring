package com.fang.common.result;

import com.fang.common.HttpStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


/**
 * 统一响应体
 *
 * @author chenjp
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "统一响应体")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 4757669095623180497L;

    public static final String CODE = "code";

    public static final String MESSAGE = "message";

    public static final String DATA = "data";

    @ApiModelProperty(value = "返回状态码标记：成功标记=200，失败标记=0")
    private int code;

    @ApiModelProperty(value = "返回信息")
    private String message;

    @ApiModelProperty(value = "返回的数据")
    private T data;

    @ApiModelProperty(value = "扩展的信息")
    private T extend;

    @ApiModelProperty(value = "异常信息")
    private Error error = new Error();

    public static <T> Result<T> success() {
        return buildResult(null, null, HttpStatus.SUCCESS, null);
    }

    public static <T> Result<T> success(T data) {
        return buildResult(data, null, HttpStatus.SUCCESS, null);
    }

    public static <T> Result<T> success(String message) {
        return buildResult(null, null, HttpStatus.SUCCESS, message);
    }

    public static <T> Result<T> success(T data, String message) {
        return buildResult(data, null, HttpStatus.SUCCESS, message);
    }

    public static <T> Result<T> success(T data, T extend, String message) {
        return buildResult(data, extend, HttpStatus.SUCCESS, message);
    }

    public static <T> Result<T> fail() {
        return buildResult(null, null, HttpStatus.FAIL, null);
    }

    public static <T> Result<T> fail(T data) {
        return buildResult(data, null, HttpStatus.FAIL, null);
    }

    public static <T> Result<T> fail(T data, Integer stackCode, String stackMessage) {
        return buildResult(data, null, HttpStatus.FAIL, null, stackCode, stackMessage);
    }

    public static <T> Result<T> fail(String message) {
        return buildResult(null, null, HttpStatus.FAIL, message);
    }

    public static <T> Result<T> fail(String message, Integer stackCode, String stackMessage) {
        return buildResult(null, null, HttpStatus.FAIL, message, stackCode, stackMessage);
    }

    public static <T> Result<T> fail(Integer stackCode, String stackMessage) {
        return buildResult(null, null, HttpStatus.FAIL, null, stackCode, stackMessage);
    }

    public static <T> Result<T> fail(T data, String message) {
        return buildResult(data, null, HttpStatus.FAIL, message);
    }

    public static <T> Result<T> fail(T data, String message, Integer stackCode, String stackMessage) {
        return buildResult(data, null, HttpStatus.FAIL, message, stackCode, stackMessage);
    }

    public static <T> Result<T> fail(T data, T extend, String message) {
        return buildResult(data, extend, HttpStatus.FAIL, message);
    }

    public static <T> Result<T> fail(T data, T extend, String message, Integer stackCode, String stackMessage) {
        return buildResult(data, extend, HttpStatus.FAIL, message, stackCode, stackMessage);
    }

    public static <T> Result<T> error() {
        return buildResult(null, null, HttpStatus.ERROR, null);
    }

    public static <T> Result<T> error(T data) {
        return buildResult(data, null, HttpStatus.ERROR, null);
    }

    public static <T> Result<T> error(T data, Integer stackCode, String stackMessage) {
        return buildResult(data, null, HttpStatus.ERROR, null, stackCode, stackMessage);
    }

    public static <T> Result<T> error(String message) {
        return buildResult(null, null, HttpStatus.ERROR, message);
    }

    public static <T> Result<T> error(String message, Integer stackCode, String stackMessage) {
        return buildResult(null, null, HttpStatus.ERROR, message, stackCode, stackMessage);
    }

    public static <T> Result<T> error(Integer stackCode, String stackMessage) {
        return buildResult(null, null, HttpStatus.ERROR, null, stackCode, stackMessage);
    }

    public static <T> Result<T> error(T data, String message) {
        return buildResult(data, null, HttpStatus.ERROR, message);
    }

    public static <T> Result<T> error(T data, String message, Integer stackCode, String stackMessage) {
        return buildResult(data, null, HttpStatus.ERROR, message, stackCode, stackMessage);
    }

    public static <T> Result<T> error(T data, T extend, String message) {
        return buildResult(data, extend, HttpStatus.ERROR, message);
    }

    public static <T> Result<T> error(T data, T extend, String message, Integer stackCode, String stackMessage) {
        return buildResult(data, extend, HttpStatus.ERROR, message, stackCode, stackMessage);
    }

    private static <T> Result<T> buildResult(T data, T extend, int code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setData(data);
        result.setMessage(message);
        result.setExtend(extend);
        return result;
    }

    private static <T> Result<T> buildResult(T data, T extend, int code, String message, Integer stackCode, String StackMessage) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setData(data);
        result.setMessage(message);
        result.setExtend(extend);
        result.getError().setCode(stackCode);
        result.getError().setMessage(StackMessage);
        return result;
    }

    /**
     * 追加扩展信息
     */
    public Result<T> extend(T extend) {
        this.setExtend(extend);
        return this;
    }

    /**
     * 追加数据条数
     */
    public Result<T> stack(Integer stackCode, String stackMessage) {
        this.error.setCode(stackCode);
        this.error.setMessage(stackMessage);
        return this;
    }
}
