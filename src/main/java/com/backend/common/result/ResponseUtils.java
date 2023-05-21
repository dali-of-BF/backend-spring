package com.backend.common.result;

import com.backend.common.HttpStatus;
import com.backend.utils.JsonMapper;
import org.springframework.security.authentication.BadCredentialsException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author FPH
 * @since 2023年4月21日10:53:32
 */

public class ResponseUtils {
    private ResponseUtils(){}

    private static void buildResult(HttpServletResponse response,Integer code,Object data) {
        Result<Object> result = new Result<>();
        result.setCode(code);
        result.setData(data);
        //如果是非200的错误异常，则error也写入状态码与错误信息
        if(!code.equals(HttpStatus.SUCCESS)){
            result.getError().setCode(code);
            result.getError().setMessage(String.valueOf(data));
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.SUCCESS);
        try {
            response.getWriter().write(JsonMapper.writeValueAsString(result));
        } catch (IOException e) {
            throw new BadCredentialsException("Logout ERROR \n Failed to decode basic authentication token");
        }
    }
//Utility classes, which are collections of static members, are not meant to be instantiated. Even abstract utility classes, which can be extended, should not have public constructors.
//Java adds an implicit public constructor to every class which does not define at least one explicitly. Hence, at least one non-public constructor should be defined.
    public static void success(HttpServletResponse response,Object data){
        buildResult(response,HttpStatus.SUCCESS,data);
    }

    public static void error(HttpServletResponse response,Object data){
        buildResult(response,HttpStatus.ERROR,data);
    }

    public static void forbidden(HttpServletResponse response,Object data){
        buildResult(response,HttpStatus.FORBIDDEN,data);
    }

}
