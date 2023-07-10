package com.backend.service.sys;

import com.backend.config.ApplicationProperties;
import com.backend.config.security.SecurityConfig;
import com.backend.domain.dto.sys.RegisterUserDTO;
import com.backend.domain.entity.sys.SysAccount;
import com.backend.exception.BusinessException;
import com.backend.mapper.sys.SysAccountMapper;
import com.backend.utils.HeaderUtils;
import com.backend.utils.JsonMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * @author FPH
 * @since 2022年8月7日15:06:16
 */
@Service
@RequiredArgsConstructor
public class SysAccountService extends ServiceImpl<SysAccountMapper, SysAccount> {
    private final HttpServletRequest request;
    private final ApplicationProperties properties;
    private final SysAccountMapper sysAccountMapper;
    private final SecurityConfig securityConfig;
    private final HeaderUtils headerUtils;

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
     * 注册
     * @param dto 保存实体
     */
    @Transactional(rollbackFor = Exception.class)
    public SysAccount register(RegisterUserDTO dto){
        //用户名不可重复
        List<SysAccount> sysAccounts = sysAccountMapper.selectList(new LambdaQueryWrapper<SysAccount>().eq(SysAccount::getUsername, dto.getUsername()));
        if(CollectionUtils.isNotEmpty(sysAccounts)){
            throw new BusinessException("账号已存在");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        SysAccount sysAccount = JsonMapper.covertValue(dto, SysAccount.class);
        sysAccount.setAppId(Optional.ofNullable(headerUtils.getAppId()).orElseThrow(()->new BusinessException("appId不存在")));
        sysAccount.setPassword(bCryptPasswordEncoder.encode(properties.getSecurity().getDefaultPassword()));
        save(sysAccount);
        return sysAccount;
    }

    /**
     * 重置密码
     * @param id 用户id
     */
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(String id) {
        SysAccount account = Optional.ofNullable(this.getById(id)).orElseThrow(() -> new BusinessException("用户不存在"));
        account.setPassword(securityConfig.passwordEncoder()
                .encode(properties.getSecurity().getDefaultPassword()));
        this.updateById(account);
    }

    /**
     * 修改用户密码
     *
     * @param id
     * @param password
     * @param oldPassword
     */
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(String id, String password, String oldPassword) {
        SysAccount account = Optional.ofNullable(this.getById(id)).orElseThrow(() -> new BusinessException("用户不存在"));
        if (!securityConfig.passwordEncoder().matches(oldPassword,account.getPassword())){
            throw new BusinessException("旧密码错误");
        }
        if (securityConfig.passwordEncoder().matches(password,account.getPassword())){
            throw new BusinessException("新密码不能同原密码相同");
        }
        account.setPassword(securityConfig.passwordEncoder().encode(password));
        this.updateById(account);
        // TODO: 2023/5/19 移除token
    }
}
