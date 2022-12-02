package com.example.test.controller

import com.example.test.services.CurrencyServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class CurrencyController(
    private val currencyService: CurrencyServiceImpl,
) {

    @GetMapping
    fun getCurrencies() = currencyService.getAllCurrenciesFromDB()

    @PostMapping
    fun saveCurrencies() = currencyService.saveCurrencies()
        .run { ResponseEntity.ok(this) }

    @PostMapping("/add")
    fun addNewCurrency(
        @RequestParam(value = "letterCode", required = true) letterCode: String,
    ) = currencyService.saveNewCurrency(letterCode)
        .run { ResponseEntity.ok(this) }
}
