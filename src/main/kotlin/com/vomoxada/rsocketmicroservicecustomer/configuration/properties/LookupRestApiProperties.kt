package com.vomoxada.rsocketmicroservicecustomer.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "lookup-rest-api")
data class LookupRestApiProperties(
    val baseUrl: String
)