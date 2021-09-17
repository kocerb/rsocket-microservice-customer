package com.vomoxada.rsocketmicroservicecustomer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class RsocketMicroserviceCustomerApplication

fun main(args: Array<String>) {
	runApplication<RsocketMicroserviceCustomerApplication>(*args)
}
