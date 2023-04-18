package com.fang.config.swagger;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author FPH
 * @since 2022年9月24日15:01:37
 * swagger配置类
 */
@Getter
@Setter
@Configuration
@EnableSwagger2
@ConfigurationProperties("swagger")
public class SwaggerConfig {

    /**
     * 自定义host
     */
    private String host=null;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                /**
                 * 扫描包
                 */
                .apis(RequestHandlerSelectors.basePackage("com.fang.controller"))
                /**
                 * 扫描在API注解的contorller
                 */
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                /**
                 * 扫描带ApiOperation注解的方法
                 */
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                /**
                 * 自定义host
                 */
                .build().host(host);
    }

    private ApiInfo apiInfo() {
        String version = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return new ApiInfoBuilder()
                .title("springboot基础框架")
                .description("fph基础框架构建")
                .version("v2."+version)
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/dali-of-BF/backend-spring.git")
                .contact(null)
                .build();
    }
}
