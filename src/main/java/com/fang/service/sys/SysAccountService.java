package com.fang.service.sys;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fang.domain.dto.SysAccountDTO;
import com.fang.domain.entity.sys.SysAccount;
import com.fang.mapper.sys.SysAccountMapper;
import com.fang.utils.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author FPH
 * @since 2022年8月7日15:06:16
 */
@Service
@RequiredArgsConstructor
public class SysAccountService extends ServiceImpl<SysAccountMapper, SysAccount> {

    /**
     * 分页
     * @param page
     * @param row
     * @return
     */
    public IPage<SysAccount> getPage(Long page, Long row){
        Page<SysAccount> sysAccountPage = new Page<>(page, row);
        return page(sysAccountPage, null);
    }

    /**
     * 保存用户
     * @param dto 保存实体
     */
    public void saveEntity(SysAccountDTO dto){
        SysAccount sysAccount = new SysAccount();
        BeanUtil.copyProperties(dto,sysAccount);
        save(sysAccount);
    }
}
