package com.example.test.model

import com.example.test.dto.CurrencyDTO
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id

@Entity
class Currency(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Long = 0,

    @Column
    var currencyName: String? = "",

    @Column
    var digitalCode: Int? = 0,

    @Column(unique = true)
    val letterCode: String = "",

    @Column
    var rate: Double? = 0.0,
) {
    fun toDto() =
        CurrencyDTO(
            currencyName = currencyName,
            digitalCode = digitalCode,
            letterCode = letterCode,
            rate = rate,
        )
}
