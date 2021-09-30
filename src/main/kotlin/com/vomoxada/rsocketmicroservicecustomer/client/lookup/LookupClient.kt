package com.vomoxada.rsocketmicroservicecustomer.client.lookup

import com.vomoxada.rsocketmicroservicecustomer.client.lookup.dto.response.ProfessionResponse
import com.vomoxada.rsocketmicroservicecustomer.configuration.properties.LookupApiProperties
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.RSocketStrategies
import org.springframework.messaging.rsocket.retrieveAndAwaitOrNull
import org.springframework.stereotype.Component
import org.springframework.util.MimeTypeUtils
import java.util.UUID

@Component
class LookupClient(
    lookupApiProperties: LookupApiProperties,
    rSocketStrategies: RSocketStrategies
) {
    private val rSocketRequester: RSocketRequester = RSocketRequester.builder()
        .rsocketStrategies(rSocketStrategies)
        .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
        .tcp(lookupApiProperties.baseUrl, lookupApiProperties.port)

    suspend fun professionsGetById(id: UUID): ProfessionResponse? {
        return rSocketRequester.route("professions.getById.{id}", id).retrieveAndAwaitOrNull()
    }
}