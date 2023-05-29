package com.backend.annotation.aspect;

import com.backend.annotation.Log;
import com.backend.domain.entity.sys.SysLog;
import com.backend.enums.sys.StatusEnum;
import com.backend.mapper.sys.SysLogMapper;
import com.backend.utils.AsyncManager;
import com.backend.utils.SecurityUtils;
import com.backend.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.NamedThreadLocal;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author FPH
 * 日志aop
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
@Order(1) //最高优先级
public class LogAspect {
    /** 计算操作消耗时间 */
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new NamedThreadLocal<>("Cost Time");
    private final HttpServletRequest request;
    private final SysLogMapper sysLogMapper;
    @Before(value = "@within(log) || @annotation(log)")
    public void boBefore(JoinPoint joinPoint, Log log) {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(log)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log log, Object jsonResult) {
        handleLog(joinPoint, log, null);
    }

    @AfterReturning(pointcut = "@within(log)", returning = "jsonResult")
    public void doAfterReturningInClass(JoinPoint joinPoint, Log log, Object jsonResult) {
        // 获取类级别上的注解信息
        Class<?> targetClass = joinPoint.getTarget().getClass();
        Log classLog = targetClass.getAnnotation(Log.class);
        handleLog(joinPoint, classLog, null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e 异常
     */
    @AfterThrowing(value = "@annotation(log)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log log, Exception e) {
        handleLog(joinPoint, log, e);
    }

    @AfterThrowing(value = "@within(log)", throwing = "e")
    public void doAfterThrowingInClass(JoinPoint joinPoint, Log log, Exception e) {
        // 获取类级别上的注解信息
        Class<?> targetClass = joinPoint.getTarget().getClass();
        Log classLog = targetClass.getAnnotation(Log.class);
        handleLog(joinPoint, classLog, e);
    }

    protected void handleLog(final JoinPoint joinPoint, Log controllerLog, final Exception e) {
        try {
            //当遇到排除请求则不记录
            Set<HttpMethod> fieldSet = new HashSet<>(Arrays.asList(controllerLog.excludeMethodType()));
            if (Boolean.TRUE.equals(fieldSet.contains(HttpMethod.resolve(request.getMethod())))) {
                return;
            }
            // 获取当前的用户名
            String username = SecurityUtils.getUsername();

            // *========数据库日志=========*//
            SysLog operLog = new SysLog();
            operLog.setStatus(StatusEnum.ENABLE.getValue());
            // TODO: 2023/5/27 请求的地址
            String ip = "UNKNOWN";
            operLog.setIp(ip);
            operLog.setUrl(StringUtils.substring(request.getRequestURI(), 0, 255));
            if (StringUtils.isNotBlank(username)) {
                operLog.setOperBy(username);
            }

            if (Objects.nonNull(e)) {
                operLog.setStatus(StatusEnum.DISABLE.getValue());
                operLog.setError(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置消耗时间
            operLog.setCostTime(TimeUtils.toDate(System.currentTimeMillis() - TIME_THREADLOCAL.get(), TimeUtils.TIME_SSS));
            //操作时间
            operLog.setOperTime(TimeUtils.toDate(TIME_THREADLOCAL.get(),TimeUtils.DATE_TIME_FORMAT));
            // 保存数据库
            AsyncManager.me().execute(new TimerTask() {
                @Override
                public void run() {
                    sysLogMapper.insert(operLog);
                }
            });
        }
        catch (Exception exp) {
            // 记录本地异常日志
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
        finally {
            TIME_THREADLOCAL.remove();
        }
    }
}
