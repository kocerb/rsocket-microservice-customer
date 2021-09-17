package com.vomoxada.rsocketmicroservicecustomer.domain.dto.request

import com.vomoxada.rsocketmicroservicecustomer.domain.enums.Gender
import java.time.LocalDate
import java.util.UUID

data class CustomerCreateRequest(
    val email: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val birthdate: LocalDate? = null,
    val gender: Gender? = null,
    val professionId: UUID? = null
)
