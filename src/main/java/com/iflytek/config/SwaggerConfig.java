package com.iflytek.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: chongchong
 * @DateTime: 2023/7/7 1:33
 * @Description: swagger配置类
 */
//开启swagger2
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    //将参数api构建出来
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
    }

    //构建工厂
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("系统接口测试文档").build();
    }
}
