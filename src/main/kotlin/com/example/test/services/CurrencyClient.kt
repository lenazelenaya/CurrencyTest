package com.example.test.services

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod.GET
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class CurrencyClient(
    @Value("\${ratesNBU.url}")
    private val url: String,
    private val restTemplate: RestTemplate,
) {

    fun getCurrencies(letterCodes: List<String>) = letterCodes.map { letterCode ->
        getCurrency(letterCode)
    }.filterNotNull()

    fun getCurrency(letterCode: String) = restTemplate.exchange(
        urlWithParam(letterCode),
        GET,
        null,
        object : ParameterizedTypeReference<List<CurrencyRecord>>() {},
    ).body?.firstOrNull()

    private fun urlWithParam(letterCode: String) = UriComponentsBuilder.fromHttpUrl(url)
        .queryParam(CODE_NAME, letterCode)
        .toUriString()

    companion object {
        private const val CODE_NAME = "valcode"
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class CurrencyRecord(
    @JsonProperty("r030")
    val digitalCode: Int = 0,
    @JsonProperty("cc")
    val letterCode: String = "",
    @JsonProperty("txt")
    val currencyName: String = "",
    @JsonProperty("rate")
    val rate: Double = 0.0,
)
