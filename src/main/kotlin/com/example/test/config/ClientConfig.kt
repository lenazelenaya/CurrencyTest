package com.example.test.config

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class ClientConfig {
    @Bean
    fun restTemplate(): RestTemplate = RestTemplateBuilder().build()
}
