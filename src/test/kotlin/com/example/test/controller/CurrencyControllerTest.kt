package com.example.test.controller

import com.example.test.dto.CurrencyDTO
import com.example.test.services.CurrencyServiceImpl
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@WebMvcTest(CurrencyController::class)
internal class CurrencyControllerTest {

    @MockkBean(relaxed = true)
    lateinit var service: CurrencyServiceImpl

    @Autowired
    lateinit var mockMvc: MockMvc

    private val testCurrencyList = listOf(
        CurrencyDTO("currencyName", 1, "letterCode", 0.01),
        CurrencyDTO("currencyName", 2, "letterCode", 0.02),
        CurrencyDTO("currencyName", 3, "letterCode", 0.03)
    )

    @Test
    fun `should call get currencies method`() {
        every { service.getAllCurrenciesFromDB() } returns testCurrencyList

        mockMvc.get("/")
            .andExpect {
                content { testCurrencyList }
                status { isOk() }
            }
    }

    @Test
    fun `should call save currencies method`() {
        val codes = testCurrencyList.map { it.letterCode }
        every { service.saveCurrencies() } returns codes

        mockMvc.post("/")
            .andExpect {
                content { codes }
                status { isOk() }
            }
        verify { service.saveCurrencies() }
    }

    @Test
    fun `should call save currency method`() {
        val code = "new currancy code"
        every { service.saveNewCurrency(code) } returns code

        mockMvc.post("/")
            .andExpect {
                content { code }
                status { isOk() }
            }
        verify { service.saveCurrencies() }
    }
}
