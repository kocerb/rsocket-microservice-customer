package com.vomoxada.rsocketmicroservicecustomer.service

import com.vomoxada.rsocketmicroservicecustomer.client.lookup.LookupClient
import com.vomoxada.rsocketmicroservicecustomer.domain.dto.request.CustomerCreateRequest
import com.vomoxada.rsocketmicroservicecustomer.domain.dto.request.CustomerUpdateRequest
import com.vomoxada.rsocketmicroservicecustomer.domain.dto.response.CustomerResponse
import com.vomoxada.rsocketmicroservicecustomer.domain.mapper.CustomerToCustomerResponseMapper
import com.vomoxada.rsocketmicroservicecustomer.domain.model.Customer
import com.vomoxada.rsocketmicroservicecustomer.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val customerToCustomerResponseMapper: CustomerToCustomerResponseMapper,
    private val lookupClient: LookupClient
) {

    suspend fun getById(id: UUID): CustomerResponse? {
        return customerRepository.findById(id)?.mapToCustomerResponse()
    }

    fun getAll(): Flow<CustomerResponse> {
        return customerRepository
            .findAll()
            .map { it.mapToCustomerResponse() }
    }

    suspend fun create(customerCreateRequest: CustomerCreateRequest): CustomerResponse {
        val customer = with(customerCreateRequest) {
            Customer(
                email = email,
                firstName = firstName,
                lastName = lastName,
                birthdate = birthdate,
                gender = gender,
                professionId = professionId
            )
        }

        return customerRepository.save(customer).mapToCustomerResponse()
    }

    suspend fun update(id: UUID, customerUpdateRequest: CustomerUpdateRequest, version: Int): CustomerResponse {
        val customer = customerRepository.findById(id) ?: throw Exception("Customer is not found with given ID: $id")
        if (customer.version != version) throw Exception("Resource has been changed by another request.")

        val updatedCustomer = with(customerUpdateRequest) {
            customer.copy(
                firstName = firstName,
                lastName = lastName,
                birthdate = birthdate,
                gender = gender,
                professionId = professionId
            )
        }

        return customerRepository.save(updatedCustomer).mapToCustomerResponse()
    }

    suspend fun delete(id: UUID) {
        customerRepository.deleteById(id)
    }

    private suspend fun Customer.mapToCustomerResponse(): CustomerResponse {
        val customerResponse = customerToCustomerResponseMapper.convert(this)!!
        val professionResponse = this.professionId?.let { professionId -> lookupClient.professionsGetById(professionId) }
        customerResponse.profession = professionResponse ?: throw Exception("Profession is not found with given ID: ${this.professionId}")
        return customerResponse
    }
}
