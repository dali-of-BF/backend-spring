package com.backend;

import com.backend.config.ApplicationProperties;
import com.backend.utils.SpringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@SpringBootTest
class BackEndSpringApplicationTests {


    @Resource
    private ApplicationProperties applicationProperties;


    @Test
    void encode(){
        System.out.println(SpringUtils.getBean(PasswordEncoder.class).encode("123456"));
    }
}
