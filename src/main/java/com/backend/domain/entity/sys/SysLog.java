package com.backend.domain.entity.sys;

import com.backend.domain.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author FPH
 * 操作日志
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_log")
public class SysLog extends BaseEntity {
    /**
     * 操作IP地址
     */
    private String ip;
    /**
     * 操作的地址
     */
    private String url;
    /**
     * 1正常0异常
     */
    private Integer status;
    /**
     * 操作时间
     */
    @JsonFormat(timezone = "Asia/Shanghai",pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date operTime;
    /**
     * 运行持续时间
     */
    @JsonFormat(timezone = "Asia/Shanghai",pattern = "HH:mm:ss.SSS")
    private Date costTime;
    /**
     * 操作人
     */
    private String operBy;
    /**
     * 错误日志
     */
    private String error;
    /**
     * 操作方法
     */
    private String method;
}
