package com.vomoxada.rsocketmicroservicecustomer.configuration

import org.springframework.boot.autoconfigure.rsocket.RSocketMessageHandlerCustomizer
import org.springframework.context.annotation.Configuration
import org.springframework.core.ParameterizedTypeReference
import org.springframework.messaging.rsocket.MetadataExtractorRegistry
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler
import org.springframework.util.MimeType
import reactor.core.publisher.Hooks
import javax.annotation.PostConstruct

@Configuration
class RSocketConfiguration : RSocketMessageHandlerCustomizer {

    // Required to extract JSON formatted metadata to key-value headers
    // See: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/messaging/rsocket/MetadataExtractor.html
    // See: https://spring.getdocs.org/en-US/spring-framework-docs/docs/spring-web-reactive/rsocket/rsocket-metadata-extractor.html
    override fun customize(messageHandler: RSocketMessageHandler) {
        messageHandler.rSocketStrategies = messageHandler.rSocketStrategies
            .mutate()
            .metadataExtractorRegistry { registry: MetadataExtractorRegistry ->
                registry.metadataToExtract(
                    MimeType.valueOf("application/json"),
                    object : ParameterizedTypeReference<Map<String, Any>>() {}
                ) { jsonMap, outputMap ->
                    outputMap.putAll(jsonMap)
                }
            }.build()
    }

    // Required to disable Dropped Error logs
    // See: https://github.com/rsocket/rsocket-java/issues/1018
    // See: https://github.com/rsocket/rsocket-java/issues/1020
    @PostConstruct
    fun configureHooks() {
        Hooks.onErrorDropped {}
    }
}