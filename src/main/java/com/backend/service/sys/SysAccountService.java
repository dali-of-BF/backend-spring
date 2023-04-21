package com.backend.service.sys;

import com.backend.constants.HeaderConstant;
import com.backend.domain.dto.SysAccountDTO;
import com.backend.domain.entity.sys.SysAccount;
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

/**
 * @author FPH
 * @since 2022年8月7日15:06:16
 */
@Service
@RequiredArgsConstructor
public class SysAccountService extends ServiceImpl<SysAccountMapper, SysAccount> {
    private final HttpServletRequest request;

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
        sysAccount.setAppId(request.getHeader(HeaderConstant.APP_ID));
        sysAccount.setPassword(bCryptPasswordEncoder.encode(sysAccount.getPassword()));
        save(sysAccount);
        return sysAccount;
    }
}
