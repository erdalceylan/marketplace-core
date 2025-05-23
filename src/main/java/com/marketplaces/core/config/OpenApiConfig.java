package com.marketplaces.core.config;

import io.swagger.v3.oas.models.media.StringSchema;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.parameters.Parameter;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenApiCustomizer globalHeaderOpenApiCustomizer() {
        return openApi -> {
            openApi.getPaths().values().forEach(pathItem ->
                    pathItem.readOperations().forEach(operation -> {
                        Parameter headerParam = new Parameter()
                                .in("header")
                                .name("accept-language") // Header adı
                                .description("Token örnek swagger header")
                                .required(false) // Zorunlu mu?
                                .schema(new StringSchema()); // Veri tipi (String, Integer, vs.)

                        operation.addParametersItem(headerParam);
                    })
            );
        };
    }
}