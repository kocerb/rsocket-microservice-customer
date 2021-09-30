package com.vomoxada.rsocketmicroservicecustomer.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "lookup-api")
data class LookupApiProperties(
    val baseUrl: String,
    val port: Int
)