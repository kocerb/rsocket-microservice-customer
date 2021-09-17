package com.vomoxada.rsocketmicroservicecustomer.service

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
    private val customerToCustomerResponseMapper: CustomerToCustomerResponseMapper
) {

    suspend fun getById(id: UUID): CustomerResponse {
        return findById(id).let { customerToCustomerResponseMapper.convert(it)!! }
    }

    fun getAll(): Flow<CustomerResponse> {
        return customerRepository
            .findAll()
            .map { customerToCustomerResponseMapper.convert(it)!! }
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

        return save(customer).let { customerToCustomerResponseMapper.convert(it)!! }
    }

    suspend fun update(id: UUID, customerUpdateRequest: CustomerUpdateRequest, version: Int): CustomerResponse {
        val customer = findById(id)
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

        return save(updatedCustomer).let { customerToCustomerResponseMapper.convert(it)!! }
    }

    suspend fun delete(id: UUID) {
        customerRepository.deleteById(id)
    }

    private suspend fun findById(id: UUID): Customer {
        return customerRepository.findById(id) ?: throw Exception("Customer is not found with given ID: $id")
    }

    private suspend fun save(customer: Customer): Customer {
        return customerRepository.save(customer)
    }
}