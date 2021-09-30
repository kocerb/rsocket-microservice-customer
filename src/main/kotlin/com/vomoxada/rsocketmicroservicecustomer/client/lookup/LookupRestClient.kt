package com.vomoxada.rsocketmicroservicecustomer.client.lookup

import com.vomoxada.rsocketmicroservicecustomer.client.lookup.dto.response.ProfessionResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@Component
class LookupRestClient(private val lookupWebClient: WebClient) {

    suspend fun getProfession(id: UUID): ProfessionResponse {
        return lookupWebClient.get()
            .uri {
                it.path("$PROFESSIONS_PATH/{id}")
                    .build(id)
            }
            .retrieve()
            .onStatus({ it.equals(HttpStatus.NOT_FOUND) }) {
                throw ResponseStatusException(HttpStatus.NOT_FOUND)
            }
            .awaitBody()
    }

    private companion object {
        private const val PROFESSIONS_PATH = "/professions"
    }
}