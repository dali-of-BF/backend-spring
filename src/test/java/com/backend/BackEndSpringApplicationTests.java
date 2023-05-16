package com.backend;

import com.backend.config.ApplicationProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class BackEndSpringApplicationTests {


    @Resource
    private ApplicationProperties applicationProperties;


    @Test
    void testClass(){
        System.out.println(applicationProperties.getSecurity().getBase64Secret());
    }
}
