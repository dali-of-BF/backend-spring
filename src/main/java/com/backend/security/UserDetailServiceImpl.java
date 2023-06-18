package com.backend.security;

import com.backend.annotation.Log;
import com.backend.domain.entity.sys.SysAccount;
import com.backend.domain.entity.sys.SysResource;
import com.backend.enums.sys.DeletedEnum;
import com.backend.enums.sys.IsSuperEnum;
import com.backend.enums.sys.StatusEnum;
import com.backend.mapper.sys.SysAccountMapper;
import com.backend.mapper.sys.SysResourceMapper;
import com.backend.security.domain.DomainUserDetails;
import com.backend.utils.HeaderUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 通过username登录实现类
 * @author FPH
 */
@Service("userDetailsService")
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final SysAccountMapper sysAccountMapper;
    private final SysResourceMapper sysResourceMapper;
    private final HeaderUtils headerUtils;
    /**
     * @param username {@link UsernamePasswordAuthenticationFilter 登录处理类}将s如何改造
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @Log
    public DomainUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String appId = headerUtils.getAppId();
        SysAccount sysAccount = Optional.ofNullable(sysAccountMapper.selectOne(new LambdaQueryWrapper<SysAccount>()
                .eq(SysAccount::getUsername, username)
                .eq(SysAccount::getAppId,appId)
                .eq(SysAccount::getDeleted, DeletedEnum.UNDELETED.getValue())))
                .orElseThrow(()->new UsernameNotFoundException("您输入的账号不存在，请重新输入"));
        if (StatusEnum.DISABLE.getValue().equals(sysAccount.getStatus())) {
            throw new DisabledException("您输入的账号已停用");
        }
        //校验通过后
        return createUserDetails(sysAccount,appId);
    }

    public DomainUserDetails createUserDetails(SysAccount account, String appId) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        String accountId = account.getId();
        List<SysResource> resources = sysResourceMapper.listByAccountId(account.getId(), appId);
        resources.forEach(sysResource -> grantedAuthorities
                .addAll(AuthorityUtils.commaSeparatedStringToAuthorityList(String.valueOf(sysResource.getId()))));
        Boolean isSuperAdmin = IsSuperEnum.ENABLE.getValue().equals(account.getIsSuper())?Boolean.TRUE:Boolean.FALSE;

        return DomainUserDetails.builder()
                .username(account.getPhone())
                .password(account.getPassword())
                .current(accountId)
                .phone(account.getPhone())
                .avatar(account.getAvatar())
                .gender(account.getGender())
                .nickname(account.getUsername())
                .authorities(grantedAuthorities)
                .isEnabled(StatusEnum.ENABLE.getValue().equals(account.getStatus()))
                .superAdmin(isSuperAdmin)
                .appId(appId)
                .build();
    }
}
