package com.fang;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BaseSpringBootApplicationTests {

    @Test
    void initTest(){
        System.out.println(1 << 16);
    }
}
