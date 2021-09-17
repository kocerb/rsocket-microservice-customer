package com.vomoxada.rsocketmicroservicecustomer.domain.model

import com.vomoxada.rsocketmicroservicecustomer.domain.enums.Gender
import com.vomoxada.rsocketmicroservicecustomer.domain.enums.Segment
import com.vomoxada.rsocketmicroservicecustomer.domain.enums.Status
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant
import java.time.LocalDate
import java.util.UUID

@Table
data class Customer @PersistenceConstructor constructor(
    @Id val id: UUID? = null,
    val email: String,
    var firstName: String? = null,
    var lastName: String? = null,
    var birthdate: LocalDate? = null,
    var gender: Gender? = null,
    var professionId: UUID? = null,
    var segment: Segment = Segment.CLASSIC,
    var status: Status = Status.ACTIVE,
    @Version var version: Int? = null,
    @CreatedDate var createdAt: Instant? = null,
    @CreatedBy var createdBy: UUID? = null,
    @LastModifiedDate var lastModifiedAt: Instant? = null,
    @LastModifiedBy var lastModifiedBy: UUID? = null,
)
