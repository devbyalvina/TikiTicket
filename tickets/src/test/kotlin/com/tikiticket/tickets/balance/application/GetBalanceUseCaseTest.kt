package com.tikiticket.tickets.balance.application

import com.tikiticket.tickets.appcore.domain.exception.CustomException
import com.tikiticket.tickets.balance.domain.Balance
import com.tikiticket.tickets.balance.domain.BalanceError
import com.tikiticket.tickets.balance.domain.BalanceService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class GetBalanceUseCaseTest {
    @Test
    fun `잔고가 존재하면 잔고를 반환한다`() {
        // Given
        val userId = "user123"
        val expectedBalance = Balance(userId, 1000L, LocalDateTime.now(), LocalDateTime.now())
        val balanceService = mockk<BalanceService> {
            every { retrieveBalance(userId) } returns expectedBalance
        }
        val useCase = GetBalanceUseCase(balanceService)

        // When
        val result = useCase(userId)

        // Then
        assertEquals(expectedBalance, result)
    }

    @Test
    fun `잔고가 존재하지 않으면 CustomException이 발생한다`() {
        // Given
        val userId = "user456"
        val balanceService = mockk<BalanceService> {
            every { retrieveBalance(userId) } returns null
        }
        val useCase = GetBalanceUseCase(balanceService)

        // When & Then
        val exception = org.junit.jupiter.api.assertThrows<CustomException> {
            useCase(userId)
        }
        assertEquals(BalanceError.BALANCE_NOT_FOUND, exception.customError)
    }
}