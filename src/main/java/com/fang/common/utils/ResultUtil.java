package com.fang.common.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class ResultUtil {

    public static ResponseEntity success(Object object) {
        Result result = new Result();
        result.setCode(Result.SUCCESS);
        result.setData(object);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    public static ResponseEntity success(String msg) {
        Result result = new Result(new HashMap());
        result.setCode(Result.SUCCESS);
        result.setMsg(msg);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    public static ResponseEntity success(String msg, Object object) {
        Result result = new Result();
        result.setCode(Result.SUCCESS);
        result.setMsg(msg);
        result.setData(object);
        return new ResponseEntity(result, HttpStatus.OK);
    }


    public static ResponseEntity success() {
        Result result = new Result(new HashMap());
        result.setCode(Result.SUCCESS);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    public static ResponseEntity error(String msg) {
        Result result = new Result(new HashMap());
        result.setCode(Result.INTERNAL_SERVER_ERROR);
        result.setMsg(msg);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    public static ResponseEntity error(String msg, Object errdata) {
        Result result = new Result();
        result.setCode(Result.INTERNAL_SERVER_ERROR);
        result.setMsg(msg);
        result.setData(errdata);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    public static ResponseEntity unauthorizedError(String msg) {
        Result result = new Result(new HashMap());
        result.setCode(Result.UNAUTHORIZED);
        result.setMsg(msg);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    public static ResponseEntity internalServerError(String msg) {
        Result result = new Result(new HashMap());
        result.setCode(Result.INTERNAL_SERVER_ERROR);
        result.setMsg(msg);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    public static ResponseEntity customError(String msg, int code) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * 业务异常返回
     *
     * @param msg
     * @return
     */
    public static ResponseEntity businessError(String msg) {
        Result result = new Result(new HashMap());
        result.setCode(Result.BUSINESS_ERROR);
        result.setMsg(msg);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    public static ResponseEntity paramError(String msg, Object errdata) {
        Result result = new Result();
        result.setCode(Result.ERROR);
        result.setMsg(msg);
        result.setData(errdata);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
