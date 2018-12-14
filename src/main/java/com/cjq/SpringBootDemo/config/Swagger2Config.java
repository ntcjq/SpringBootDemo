package com.cjq.SpringBootDemo.config;

/**
 * @Author: cuijq
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2
 * 访问方式http://localhost:8888/swagger-ui.html
 * 可以通过controller重定向
 *     @GetMapping()
 *     public String index()
 *     {
 *         return redirect("/swagger-ui.html");
 *     }
 */
@Configuration
/**
 * 开启在线接口文档
 */
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket controllerApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cjq.SpringBootDemo.controller"))
                .paths(PathSelectors.any())
                .build();

    }
    /**
     * 添加摘要信息
     */
    private ApiInfo apiInfo()
    {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                .title("标题：财智系统_接口文档")
                .description("描述：用户IU3流程传递和更改信息")
                .contact(new Contact("SpringBootDemo", null, null))
                .version("版本号:" + 1.0)
                .build();
    }
}
