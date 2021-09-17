package com.vomoxada.rsocketmicroservicecustomer.domain.dto.response

import com.vomoxada.rsocketmicroservicecustomer.domain.enums.Gender
import com.vomoxada.rsocketmicroservicecustomer.domain.enums.Segment
import com.vomoxada.rsocketmicroservicecustomer.domain.enums.Status
import java.time.Instant
import java.time.LocalDate
import java.util.UUID

data class CustomerResponse(
    val id: UUID,
    var email: String,
    var firstName: String?,
    var lastName: String?,
    var birthdate: LocalDate?,
    var gender: Gender?,
    var professionId: UUID?,
    var segment: Segment,
    var status: Status,
    var version: Int?,
    var createdAt: Instant?,
    var createdBy: UUID?,
    var lastModifiedAt: Instant?,
    var lastModifiedBy: UUID?,
)
