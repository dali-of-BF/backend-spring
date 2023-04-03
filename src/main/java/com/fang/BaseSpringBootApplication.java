package com.fang;


import com.fang.config.ApplicationProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author FPH
 * @since 2022.04.27 03点09分
 */
@SpringBootApplication
@MapperScan("com.fang.mapper")
@EnableConfigurationProperties({ApplicationProperties.class})
public class BaseSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseSpringBootApplication.class, args);
    }

}
