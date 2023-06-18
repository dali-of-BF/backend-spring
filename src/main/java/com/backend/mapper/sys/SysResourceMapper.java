package com.backend.mapper.sys;

import com.backend.domain.entity.sys.SysResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author FPH
 */
@Mapper
public interface SysResourceMapper extends BaseMapper<SysResource> {
    // TODO: 2023/4/21 资源关联表与角色关联表还没新建，先用资源表的appId来查询
    /**
     * 通过账号id查询所属的resource资源
     * @param id
     * @param appId
     * @return
     */
    List<SysResource> listByAccountId(@Param("id") String id,@Param("appId") String appId);

    /**
     * 通过url查询那些角色拥有此权限
     * @param url
     * @param sysCode
     * @return
     */
    Set<String> findRoleNameByMenuUrl(@Param("url") String url,
                                      @Param("sysCode")String sysCode);
}
