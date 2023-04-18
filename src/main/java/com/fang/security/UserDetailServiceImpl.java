package com.fang.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fang.constants.Constant;
import com.fang.domain.entity.sys.SysAccount;
import com.fang.enums.sys.DeletedEnum;
import com.fang.enums.sys.StatusEnum;
import com.fang.mapper.sys.SysAccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 通过username登录实现类
 * @author FPH
 */
@Service("userDetailsService")
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final SysAccountMapper sysAccountMapper;
    /**
     * @param s {@link UsernamePasswordAuthenticationFilterImpl 登录处理类}将s如何改造
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        String[] usernameAndAppId = s.split(Constant.DASH);
        String username = usernameAndAppId[0];
        String appId = usernameAndAppId[1];
        SysAccount sysAccount = Optional.ofNullable(sysAccountMapper.selectOne(new LambdaQueryWrapper<SysAccount>()
                .eq(SysAccount::getUsername, username)
                .eq(SysAccount::getAppId,appId)
                .eq(SysAccount::getDeleted, DeletedEnum.UNDELETED.getValue())))
                .orElseThrow(()->new UsernameNotFoundException("您输入的账号不存在，请重新输入"));
        if (StatusEnum.DISABLE.getValue().equals(sysAccount.getStatus())) {
            throw new DisabledException("您输入的账号已停用");
        }
        //校验通过后
        return null;
    }
}
