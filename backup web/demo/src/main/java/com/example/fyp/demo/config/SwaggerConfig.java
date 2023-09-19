package com.example.fyp.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi

public class SwaggerConfig {
    @Bean
    public Docket restApi(){
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("api interface")
            .apiInfo(apiInfo("Spring Boot use swagger 2 build RESTful APIs","1.0"))
            .useDefaultResponseMessages(true)
            .forCodeGeneration(false)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.example.fyp.demo.controller"))
            .paths(PathSelectors.any())
            .build();

        }
        private ApiInfo apiInfo(String title, String version){
            return new ApiInfoBuilder()
                    .title(title)
                    .description("duoduoguanzhu")
                    .termsOfServiceUrl("https://blog.cdsn.net/xqnode")
                    .contact(new Contact("xqnode","https://blog.csdn.net/xqnode","xiaqingweb@163.com"))
                    .version(version)
                    .build();
        }
}

