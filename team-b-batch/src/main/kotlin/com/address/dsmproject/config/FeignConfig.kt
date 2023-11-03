package com.address.dsmproject.config

import com.address.dsmproject.BASE_PACKAGE
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignAutoConfiguration
import org.springframework.context.annotation.Configuration

@EnableFeignClients(basePackages = [BASE_PACKAGE])
@ImportAutoConfiguration(FeignAutoConfiguration::class)
@Configuration
class FeignConfig
