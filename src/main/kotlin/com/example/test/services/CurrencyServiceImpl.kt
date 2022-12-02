package com.example.test.services

import com.example.test.model.Currency
import com.example.test.repository.CurrencyRepo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CurrencyServiceImpl(
    private val currencyRepo: CurrencyRepo,
    private val currencyClient: CurrencyClient,
) {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun getAllCurrenciesFromDB() = currencyRepo.findAll().map { it.toDto() }

    fun saveCurrencies(): List<String> {
        val currencyList = allCurrencyRecordsForDatabase()
            .map { record ->
                currencyRepo.findByLetterCode(record.letterCode).apply {
                    currencyName = record.currencyName
                    digitalCode = record.digitalCode
                    rate = record.rate
                }
            }
        currencyRepo.saveAllAndFlush(currencyList)
        return currencyList.map { it.letterCode }
    }

    fun saveNewCurrency(letterCode: String): String {
        currencyClient.getCurrency(letterCode)?.run {
            currencyRepo.save(
                Currency(
                    letterCode = letterCode,
                    currencyName = currencyName,
                    digitalCode = digitalCode,
                    rate = rate,
                )
            )
        }
        return letterCode
    }

    private fun allCurrencyRecordsForDatabase() = currencyClient.getCurrencies(allLetterCodesFromDb())

    private fun allLetterCodesFromDb() = currencyRepo.findAll().map { it.letterCode }
}
