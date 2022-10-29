package com.fang;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BaseSpringBootApplicationTests {

    @Autowired
    private StringEncryptor stringEncryptor;

    /**
     * 加密
     */
    @Test
    void encrypt(){
        System.out.println(stringEncryptor.decrypt("bfjILfCxGFVDa+zyvyO0fZ2SLv2H8VzE1XAf0rb6m7J/71ZXrjlahVoTm5FD2Zvh"));
    }
}
