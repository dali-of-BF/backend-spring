package com.fang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fang.pojo.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author FPH
 * @since 2022年4月27日04:04:16
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 通过id集合查询版本
     * @param ids
     * @return
     */
    List<Integer> queryVersionById(@Param("ids") Integer[] ids);
}
