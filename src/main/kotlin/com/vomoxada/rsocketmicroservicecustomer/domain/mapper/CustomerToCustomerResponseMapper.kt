package com.vomoxada.rsocketmicroservicecustomer.domain.mapper

import com.vomoxada.rsocketmicroservicecustomer.domain.dto.response.CustomerResponse
import com.vomoxada.rsocketmicroservicecustomer.domain.model.Customer
import org.mapstruct.Mapper
import org.springframework.core.convert.converter.Converter

@Mapper
interface CustomerToCustomerResponseMapper: Converter<Customer, CustomerResponse>