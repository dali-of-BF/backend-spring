package com.fang;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BaseSpringBootApplicationTests {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    void initTest(){
        System.out.println(stringEncryptor.encrypt("123456"));
    }
}
