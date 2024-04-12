package com.tikiticket.tickets.balance.application

import com.tikiticket.tickets.balance.application.exception.BalanceError
import com.tikiticket.tickets.balance.application.exception.BalanceException
import com.tikiticket.tickets.balance.domain.Balance
import com.tikiticket.tickets.balance.domain.BalanceHistoryService
import com.tikiticket.tickets.balance.domain.BalanceService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ChangeBalanceUseCaseTest {
    @Test
    fun `유효한 Command 를 입력받은 경우 변경된 잔고를 리턴한다`() {

        // Given
        val userId = "user123"
        val amount = 100L
        val balance = Balance(userId, 500L, LocalDateTime.now(), LocalDateTime.now())
        val command = ChangeBalanceCommand(userId, "CHARGE", amount)
        val balanceService = mockk<BalanceService> {
            every { retrieveBalance(userId) } returns balance
            every { storeBalance(any()) } answers {
                val changedBalance = firstArg() as Balance
                // 변경된 잔고를 반환하도록 설정
                balance.copy(balanceAmount = changedBalance.balanceAmount)
            }
        }
        val balanceHistoryService = mockk<BalanceHistoryService>(relaxed = true)
        val useCase = ChangeBalanceUseCase(balanceService, balanceHistoryService)

        // When
        val result = useCase(command)

        // Then
        assertEquals(balance.copy(balanceAmount = 600L), result)
        verify(exactly = 1) { balanceService.retrieveBalance(userId) }
        verify(exactly = 1) { balanceService.storeBalance(any()) }
    }

    @Test
    fun `충전한다`() {
        // Given
        val userId = "user123"
        val amount = 500L
        val balance = Balance(userId, 100L, LocalDateTime.now(), LocalDateTime.now())
        val balanceService = mockk<BalanceService> {
            every { retrieveBalance(userId) } returns balance
            every { storeBalance(any()) } returns balance.copy(balanceAmount = balance.balanceAmount + amount)
        }
        val balanceHistoryService = mockk<BalanceHistoryService>(relaxed = true)
        val changeBalanceUseCase = ChangeBalanceUseCase(balanceService, balanceHistoryService)
        val command = ChangeBalanceCommand(userId, "CHARGE", amount)

        // When
        val result = changeBalanceUseCase(command)

        // Then
        assertEquals(balance.userId, result.userId)
        assertEquals(balance.balanceAmount + amount, result.balanceAmount)
        verify(exactly = 1) { balanceHistoryService.storeBalanceHistory(any()) }
    }

    @Test
    fun `결제한다`() {
        // Given
        val userId = "user123"
        val amount = 500L
        val balance = Balance(userId, 1000L, LocalDateTime.now(), LocalDateTime.now())
        val balanceService = mockk<BalanceService> {
            every { retrieveBalance(userId) } returns balance
            every { storeBalance(any()) } returns balance.copy(balanceAmount = balance.balanceAmount - amount)
        }
        val balanceHistoryService = mockk<BalanceHistoryService>(relaxed = true)
        val changeBalanceUseCase = ChangeBalanceUseCase(balanceService, balanceHistoryService)
        val command = ChangeBalanceCommand(userId, "PAY", amount)

        // When
        val result = changeBalanceUseCase(command)

        // Then
        assertEquals(balance.userId, result.userId)
        assertEquals(balance.balanceAmount - amount, result.balanceAmount)
        verify(exactly = 1) { balanceHistoryService.storeBalanceHistory(any()) }
    }



    @Test
    fun `amount가 0 이면 INVALID_AMOUNT_PARAMETER exception을 발생한다`() {
        // Given
        val userId = "user123"
        val amount = 0L
        val command = ChangeBalanceCommand(userId, "CHARGE", amount)
        val balanceService = mockk<BalanceService>()
        val balanceHistoryService = mockk<BalanceHistoryService>(relaxed = true)
        val useCase = ChangeBalanceUseCase(balanceService, balanceHistoryService)

        // When & Then
        val exception = assertThrows(BalanceException::class.java) {
            useCase(command)
        }
        assertEquals(BalanceError.INVALID_AMOUNT_PARAMETER, exception.error)
    }

    @Test
    fun `amount가 음수면 INVALID_AMOUNT_PARAMETER exception을 발생한다`() {
        // Given
        val userId = "user123"
        val amount = -100L
        val command = ChangeBalanceCommand(userId, "CHARGE", amount)
        val balanceService = mockk<BalanceService>()
        val balanceHistoryService = mockk<BalanceHistoryService>(relaxed = true)
        val useCase = ChangeBalanceUseCase(balanceService, balanceHistoryService)

        // When & Then
        val exception = assertThrows(BalanceException::class.java) {
            useCase(command)
        }
        assertEquals(BalanceError.INVALID_AMOUNT_PARAMETER, exception.error)
    }
}