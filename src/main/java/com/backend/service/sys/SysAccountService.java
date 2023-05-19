package com.backend.service.sys;

import com.backend.config.ApplicationProperties;
import com.backend.constants.HeaderConstant;
import com.backend.domain.dto.SysAccountDTO;
import com.backend.domain.entity.sys.SysAccount;
import com.backend.exception.BusinessException;
import com.backend.mapper.sys.SysAccountMapper;
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
    public SysAccount register(SysAccountDTO dto){
        //用户名不可重复
        List<SysAccount> sysAccounts = sysAccountMapper.selectList(new LambdaQueryWrapper<SysAccount>().eq(SysAccount::getUsername, dto.getUsername()));
        if(CollectionUtils.isNotEmpty(sysAccounts)){
            throw new BusinessException("账号已存在");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        SysAccount sysAccount = JsonMapper.covertValue(dto, SysAccount.class);
        sysAccount.setAppId(Optional.ofNullable(request.getHeader(HeaderConstant.APP_ID)).orElseThrow(()->new BusinessException("appId不存在")));
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
        account.setPassword(properties.getSecurity().getDefaultPassword());
        this.updateById(account);
    }
}
