package com.fang;

import com.fang.utils.BeanUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
class BaseSpringBootApplicationTests {

    @Autowired
    DataSource dataSource;
    @Test
    void contextLoads() {
        try {
            System.out.println(dataSource.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test(){
        String[] asd={"111","222","22233"};
        System.out.println(BeanUtil.test(asd));
    }

}
