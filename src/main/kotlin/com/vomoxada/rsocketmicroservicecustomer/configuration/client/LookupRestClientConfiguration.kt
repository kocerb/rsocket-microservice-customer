package com.vomoxada.rsocketmicroservicecustomer.configuration.client

import com.vomoxada.rsocketmicroservicecustomer.configuration.properties.LookupRestApiProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class LookupRestClientConfiguration {

    @Bean
    fun lookupWebClient(lookupProperties: LookupRestApiProperties): WebClient {
        return WebClient.builder()
            .baseUrl(lookupProperties.baseUrl)
            .build()
    }
}
