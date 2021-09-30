package com.vomoxada.rsocketmicroservicecustomer.controller

import com.vomoxada.rsocketmicroservicecustomer.domain.dto.request.CustomerCreateRequest
import com.vomoxada.rsocketmicroservicecustomer.domain.dto.request.CustomerUpdateRequest
import com.vomoxada.rsocketmicroservicecustomer.domain.dto.response.CustomerResponse
import com.vomoxada.rsocketmicroservicecustomer.service.CustomerService
import kotlinx.coroutines.flow.toList
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/customers")
class CustomerRestController(
    private val customerService: CustomerService
) {

    @GetMapping
    suspend fun getCustomers(): List<CustomerResponse> {
        return customerService.getAll().toList()
    }

    @GetMapping("/{id}")
    suspend fun getCustomerById(@PathVariable id: UUID): CustomerResponse {
        return customerService.getById(id)
    }

    @PostMapping
    suspend fun createCustomer(@RequestBody customerCreateRequest: CustomerCreateRequest): CustomerResponse {
        return customerService.create(customerCreateRequest)
    }

    @PutMapping("/{id}")
    suspend fun updateCustomer(
        @PathVariable id: UUID,
        @RequestBody customerUpdateRequest: CustomerUpdateRequest,
        @RequestHeader version: Int
    ): CustomerResponse {
        return customerService.update(id, customerUpdateRequest, version)
    }

    @DeleteMapping("/{id}")
    suspend fun deleteCustomer(@PathVariable id: UUID) {
        return customerService.delete(id)
    }
}