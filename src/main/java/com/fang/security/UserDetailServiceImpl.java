package com.fang.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fang.domain.entity.sys.SysAccount;
import com.fang.enums.sys.DeletedEnum;
import com.fang.exception.BusinessException;
import com.fang.mapper.sys.SysAccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author FPH
 */
@Service("userDetailsService")
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final SysAccountMapper sysAccountMapper;
    /**
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysAccount sysAccount = Optional.ofNullable(sysAccountMapper.selectOne(new LambdaQueryWrapper<SysAccount>()
                .eq(SysAccount::getUsername, s)
                .eq(SysAccount::getDeleted, DeletedEnum.UNDELETED.getValue())))
                .orElseThrow(()->new BusinessException("用户不存在"));

        return null;
    }
}
