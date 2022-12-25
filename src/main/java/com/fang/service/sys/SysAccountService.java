package com.fang.service.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fang.config.security.SecurityConfig;
import com.fang.domain.dto.SysAccountDTO;
import com.fang.domain.entity.sys.SysAccount;
import com.fang.exception.BusinessException;
import com.fang.mapper.sys.SysAccountMapper;
import com.fang.utils.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author FPH
 * @since 2022年8月7日15:06:16
 */
@Service
@RequiredArgsConstructor
public class SysAccountService extends ServiceImpl<SysAccountMapper, SysAccount> {

    private final SecurityConfig securityConfig;
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
        //密码加密
        dto.setPassword(securityConfig.encoder().encode(dto.getPassword()));
        //如果username重复，则抛出异常
        List<SysAccount> list = list(new LambdaQueryWrapper<SysAccount>()
                .eq(SysAccount::getUsername, sysAccount.getUsername()));
        if(CollectionUtils.isNotEmpty(list)){
            throw new BusinessException("账号名已存在");
        }
        save(sysAccount);
    }

    /**
     * 登录
     * @return
     */
    public SysAccount login(String username,String password){
        List<SysAccount> list = list(new LambdaQueryWrapper<SysAccount>()
                .eq(SysAccount::getUsername, username));
        SysAccount account = list.stream().findFirst()
                .orElseThrow(() -> new BusinessException("账号不存在"));
        if (Boolean.FALSE.equals(securityConfig.encoder()
                .matches(password,account.getPassword()))) {
            throw new BusinessException("密码错误");
        }
        return account;
    }
}
