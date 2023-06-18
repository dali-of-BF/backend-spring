package com.backend.utils;

import com.backend.constants.HeaderConstant;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author FPH
 * @since 2023年6月18日19:08:02
 *
 */
@Component
public class HeaderUtils {

    @Resource
    private HttpServletRequest request;
    private HeaderUtils() {
    }

    public String getAppId(){
        return request.getHeader(HeaderConstant.APP_ID);
    }

    public String getHeader(String header){
        return request.getHeader(header);
    }
}
