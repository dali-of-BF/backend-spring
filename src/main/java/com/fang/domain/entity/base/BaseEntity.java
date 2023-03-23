package com.fang.domain.entity.base;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author FPH
 * @since 2022年12月29日10:27:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 创建者
     */
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "created_date", fill = FieldFill.INSERT)
    private Date createdDate;

    /**
     * 更新者
     */
    @TableField(value = "last_modified_by", fill = FieldFill.INSERT_UPDATE)
    private String lastModifiedBy;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "last_modified_date", fill = FieldFill.INSERT_UPDATE)
    private Date lastModifiedDate;

    /**
     * 删除标志位
     */
    @TableField(value = "deleted")
    @TableLogic
    private Integer deleted;
}