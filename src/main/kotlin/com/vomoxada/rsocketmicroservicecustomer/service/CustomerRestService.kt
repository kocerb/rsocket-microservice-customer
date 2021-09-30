package com.vomoxada.rsocketmicroservicecustomer.service

import com.vomoxada.rsocketmicroservicecustomer.client.lookup.LookupRestClient
import com.vomoxada.rsocketmicroservicecustomer.client.lookup.dto.response.ProfessionResponse
import com.vomoxada.rsocketmicroservicecustomer.domain.dto.response.CustomerResponse
import com.vomoxada.rsocketmicroservicecustomer.domain.mapper.CustomerToCustomerResponseMapper
import com.vomoxada.rsocketmicroservicecustomer.domain.model.Customer
import com.vomoxada.rsocketmicroservicecustomer.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@Service
class CustomerRestService(
    private val customerRepository: CustomerRepository,
    private val customerToCustomerResponseMapper: CustomerToCustomerResponseMapper,
    private val lookupRestClient: LookupRestClient
) {

    suspend fun getById(id: UUID): CustomerResponse {
        return customerRepository.findById(id)?.mapToCustomerResponse()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Customer is not found with given ID: $id")
    }

    fun getAll(): Flow<CustomerResponse> {
        return customerRepository
            .findAll()
            .map { it.mapToCustomerResponse() }
    }

    private suspend fun Customer.mapToCustomerResponse(): CustomerResponse {
        val customerResponse = customerToCustomerResponseMapper.convert(this)!!
        val professionResponse = getProfessionResponse()
        customerResponse.profession = professionResponse
        return customerResponse
    }

    private suspend fun Customer.getProfessionResponse(): ProfessionResponse? {
        return this.professionId?.let { professionId ->
            try {
                lookupRestClient.getProfession(professionId)
            } catch (ex: ResponseStatusException) {
                if (ex.status == HttpStatus.NOT_FOUND)
                    throw ResponseStatusException(HttpStatus.NOT_FOUND, "Profession is not found with given ID: ${this.professionId}")
                else
                    throw ex
            }
        }
    }
}
