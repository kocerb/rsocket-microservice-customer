package com.vomoxada.rsocketmicroservicecustomer.domain.dto.request

import com.vomoxada.rsocketmicroservicecustomer.domain.enums.Gender
import java.time.LocalDate
import java.util.UUID

data class CustomerUpdateRequest(
    val firstName: String?,
    val lastName: String?,
    val birthdate: LocalDate?,
    val gender: Gender?,
    val professionId: UUID?
)
