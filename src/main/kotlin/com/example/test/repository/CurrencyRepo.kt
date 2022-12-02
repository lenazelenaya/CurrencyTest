package com.example.test.repository

import com.example.test.model.Currency
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CurrencyRepo : JpaRepository<Currency, Long> {
    fun findByLetterCode(letterCode: String): Currency
}
