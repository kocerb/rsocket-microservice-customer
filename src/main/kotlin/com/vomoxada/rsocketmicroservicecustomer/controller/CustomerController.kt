package com.vomoxada.rsocketmicroservicecustomer.controller

import com.vomoxada.rsocketmicroservicecustomer.domain.dto.request.CustomerCreateRequest
import com.vomoxada.rsocketmicroservicecustomer.domain.dto.request.CustomerUpdateRequest
import com.vomoxada.rsocketmicroservicecustomer.domain.dto.response.CustomerResponse
import com.vomoxada.rsocketmicroservicecustomer.service.CustomerService
import kotlinx.coroutines.flow.Flow
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller
import java.util.UUID

@Controller
class CustomerController(
    private val customerService: CustomerService
) {

    @MessageMapping("customers.getAll")
    fun getCustomers(): Flow<CustomerResponse> {
        return customerService.getAll()
    }

    @MessageMapping("customers.getById.{id}")
    suspend fun getCustomerById(@DestinationVariable id: UUID): CustomerResponse? {
        return customerService.getById(id)
    }

    @MessageMapping("customers.create")
    suspend fun createCustomer(@Payload customerCreateRequest: CustomerCreateRequest): CustomerResponse {
        return customerService.create(customerCreateRequest)
    }

    @MessageMapping("customers.update.{id}")
    suspend fun updateCustomer(
        @DestinationVariable id: UUID,
        @Payload customerUpdateRequest: CustomerUpdateRequest,
        @Header version: Int
    ): CustomerResponse {
        return customerService.update(id, customerUpdateRequest, version)
    }

    @MessageMapping("customers.delete.{id}")
    suspend fun deleteCustomer(@DestinationVariable id: UUID) {
        return customerService.delete(id)
    }
}