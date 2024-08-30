package com.reply.pay.bridgeFabrick.demo.configuration;


import com.reply.pay.bridgeFabrick.demo.responseHandler.CustomResponseErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate restTemplate(@Value("${service.baseUrl}") String rootUri,
                                     @Value("${service.auth-schema}") String authSchema,
                                     @Value("${service.api-key}") String apiKey,
                                     RestTemplateBuilder builder) {

        return builder.rootUri(rootUri)
                .errorHandler(new CustomResponseErrorHandler())
                .defaultHeader("Auth-Schema", authSchema)
                .defaultHeader("Api-Key", apiKey)
                .build();
    }
}
