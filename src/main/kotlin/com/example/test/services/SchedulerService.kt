package com.example.test.services

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.concurrent.Executors

@Service
@EnableScheduling
class SchedulerService(
    private val currencyService: CurrencyServiceImpl,
) {

    @Scheduled(fixedDelay = SCHEDULED_DELAY, initialDelay = INITIAL_DELAY)
    fun saveCurrenciesBySchedule() = CoroutineScope(Executors.newSingleThreadExecutor().asCoroutineDispatcher())
        .launch {
            currencyService.saveCurrencies()
        }

    companion object {
        const val SCHEDULED_DELAY = 60L * 60L * 1000L
        const val INITIAL_DELAY = 5L * 60L * 1000L
    }
}
