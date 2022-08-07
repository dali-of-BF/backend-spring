package com.fang.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author FPH
 * @since 2022.04.27 03点46分
 */
@TableName("sys_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @Email
    private String email;

    private Integer state;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableLogic
    private Integer deleted;
}
