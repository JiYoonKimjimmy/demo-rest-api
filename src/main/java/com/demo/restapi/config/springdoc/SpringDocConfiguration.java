package com.demo.restapi.config.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${springdoc.version}")
    private String version;

    @Value("${springdoc.title}")
    private String title;


    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(info(version, title))
                .externalDocs(new ExternalDocumentation()
                        .description("Go to GitHub repository")
                        .url("https://github.com/JiYoonKimjimmy/demo-rest-api.git")
                );
    }

    private Info info(String version, String title) {
        return new Info()
                .title(title)
                .description(description())
                .version(version);
    }

    private String description() {
        return "**Spring Boot 기반 REST API 입니다.<br><br>" +
                "소셜 로그인은 [여기서](" + baseUrl + "/social/login) 해주세요.**";
    }
}
