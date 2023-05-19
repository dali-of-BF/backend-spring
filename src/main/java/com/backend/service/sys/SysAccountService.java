package com.backend.service.sys;

import com.backend.config.ApplicationProperties;
import com.backend.constants.HeaderConstant;
import com.backend.domain.dto.SysAccountDTO;
import com.backend.domain.entity.sys.SysAccount;
import com.backend.exception.BusinessException;
import com.backend.mapper.sys.SysAccountMapper;
import com.backend.utils.JsonMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        SysAccount sysAccount = JsonMapper.covertValue(dto, SysAccount.class);
        sysAccount.setAppId(Optional.ofNullable(request.getHeader(HeaderConstant.APP_ID)).orElseThrow(()->new BusinessException("appId不存在")));
        sysAccount.setPassword(bCryptPasswordEncoder.encode(properties.getSecurity().getDefaultPassword()));
        save(sysAccount);
        return sysAccount;
    }
}
