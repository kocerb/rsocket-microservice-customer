package com.vomoxada.rsocketmicroservicecustomer.repository

import com.vomoxada.rsocketmicroservicecustomer.domain.model.Customer
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.UUID

interface CustomerRepository: CoroutineCrudRepository<Customer, UUID>