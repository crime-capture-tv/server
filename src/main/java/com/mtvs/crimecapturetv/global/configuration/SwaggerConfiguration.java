package com.mtvs.crimecapturetv.global.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "⚠️CrimeCaptureTV⚠️",
                description = "안녕하세요 개발과 세발사이 팀입니다! \n" +
                        "\n배포링크 👉 [클릭](http://localhost:8080/)\n",
                version = "v1"
        )
)
@Configuration
public class SwaggerConfiguration {

    @Bean
    public GroupedOpenApi firstOpenApi() {
        String[] paths = {
                "com.mtvs.crimecapturetv"
        };

        return GroupedOpenApi
                .builder()
                .group("CCTV 스웨거")
                .packagesToScan(paths)
                .build();
    }
}
