package com.fang.security;

import com.fang.utils.SecurityUtils;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * @author FPH <br\>
 * 审计<br\>
 *  {@see <a href = "https://blog.csdn.net/wiselyman/article/details/84917143">审计作用</a> }
 */
public class SpringSecurityAuditorAware implements AuditorAware<String> {
    /**
     * @return
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityUtils.getCurrentUserLogin().orElse("anonymous"));
    }
}
