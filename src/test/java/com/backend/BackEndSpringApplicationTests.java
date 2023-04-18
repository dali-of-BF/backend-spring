package com.backend;

import com.backend.config.ApplicationProperties;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class BackEndSpringApplicationTests {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Resource
    private ApplicationProperties applicationProperties;

    /**
     * 加密
     */
    @Test
    void encrypt(){
        System.out.println(stringEncryptor.decrypt("bfjILfCxGFVDa+zyvyO0fZ2SLv2H8VzE1XAf0rb6m7J/71ZXrjlahVoTm5FD2Zvh"));
    }

    @Test
    void testClass(){
        System.out.println(applicationProperties.getSecurity().getBase64Secret());
    }
}
