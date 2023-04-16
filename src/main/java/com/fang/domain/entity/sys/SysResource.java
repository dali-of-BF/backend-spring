package com.fang.domain.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fang.domain.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author FPH
 * @since 2023年4月15日22:40:46
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_resource")
public class SysResource extends BaseEntity {
    private static final long serialVersionUID = -2740448887007453452L;
    /**
     * url
     */
    private String resourceUri;
    /**
     * 标签
     */
    private String tag;
    /**
     * 备注
     */
    private String remark;
    /**
     * 请求方法
     */
    private String resourceMethod;

    /**
     * 应用id
     */
    private String appId;

}
