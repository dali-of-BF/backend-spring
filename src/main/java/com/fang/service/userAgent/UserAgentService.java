package com.fang.service.userAgent;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author FPH
 * @since 2022年11月18日11:33:02
 * 浏览器解析工具
 */
@Service
public class UserAgentService {

    /**
     * 获取用户的Agent
     * @param request
     * @return
     */
    public UserAgent getAgent(HttpServletRequest request){
        return  UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
    }

    /**
     * 获取浏览器信息
     * @param request
     * @return
     */
    public Browser getBrowserDetail(HttpServletRequest request){
        UserAgent agent = getAgent(request);
        return agent.getBrowser();
    }

    /**
     * 获取操作系统对象
     * @param request
     * @return
     */
    public Browser getOperationSystem(HttpServletRequest request){
        UserAgent agent = getAgent(request);
        return agent.getBrowser();
    }
}
