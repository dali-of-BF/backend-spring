package com.backend.config.swagger;

import com.backend.constants.SwaggerGroupConstants;
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
    public Docket docketDefault() {
        return buildDocket("com.backend.controller",SwaggerGroupConstants.DEFAULT);
    }

//    @Bean
//    public Docket docketLogin() {
//        return buildDocket("com.backend.controller.login",SwaggerGroupConstants.LOGIN_API);
//    }
//
//    @Bean
//    public Docket docketAccount() {
//        return buildDocket("com.backend.controller.account",SwaggerGroupConstants.ACCOUNT_API);
//    }
//
//    @Bean
//    public Docket docketMonitor() {
//        return buildDocket("com.backend.controller.monitor",SwaggerGroupConstants.MONITOR_API);
//    }
//
//    @Bean
//    public Docket docketResource() {
//        return buildDocket("com.backend.controller.resource",SwaggerGroupConstants.RESOURCE_API);
//    }

//    @Bean
//    public Docket docketTest() {
//        return buildDocket("com.backend.controller.test",SwaggerGroupConstants.TEST_API);
//    }
    private ApiInfo apiInfo() {
        String version = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return new ApiInfoBuilder()
                .title("BACKEND SPRING API")
                .description("fph基础框架构建")
                .version("v2."+version)
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/dali-of-BF/backend-spring.git")
                .contact(null)
                .build();
    }

    private Docket buildDocket(String basePackage,String groupName){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName(groupName)
                .select()
                /*
                  扫描包
                 */
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                /*
                  扫描在API注解的contorller
                 */
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                /*
                  扫描带ApiOperation注解的方法
                 */
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                /*
                  自定义host
                 */
                .build().host(host);
    }
}
