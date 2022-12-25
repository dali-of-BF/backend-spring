package com.fang.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserCreateDTO implements Serializable {
    private String id;

    @NotBlank(message = "姓名不可为空")
    @Length(max = 36,message = "用户名不可超出36位")
    private String name;

    /**
     * 性别 0女1男
     */
    private Integer gender;

    /**
     * 年龄
     */
    @NotNull(message = "年龄不可为空")
    private Integer age;
}
