package com.fang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fang.pojo.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author FPH
 * @since 2022年4月27日04:04:16
 */
@Repository
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
