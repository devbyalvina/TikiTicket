package com.tikiticket.tickets.aggregate.balance.application

import com.tikiticket.tickets.aggregate.balance.application.ChangeBalanceCommand
import com.tikiticket.tickets.aggregate.balance.application.ChangeBalanceUseCase
import com.tikiticket.tickets.aggregate.balance.domain.Balance
import com.tikiticket.tickets.aggregate.balance.domain.BalanceService
import com.tikiticket.tickets.aggregate.balance.domain.TransactionType
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ChangeBalanceUseCaseTest {
    @Test
    fun `잔고 충전에 성공한다`() {
        // Given
        val userId = "user123"
        val transactionType = TransactionType.CHARGE
        val amount = 100L
        val command = ChangeBalanceCommand(userId, transactionType, amount)

        val balanceService = mockk<BalanceService>()
        val currentDateTime = LocalDateTime.now()
        val changedBalance = Balance(1L, userId, 500L, currentDateTime, currentDateTime)
        every { balanceService.changeBalance(userId, amount, TransactionType.CHARGE, any()) } returns changedBalance

        val changeBalanceUseCase = ChangeBalanceUseCase(balanceService)

        // When
        val result = changeBalanceUseCase(command)

        // Then
        verify(exactly = 1) { balanceService.changeBalance(userId, amount, TransactionType.CHARGE, any()) }

        assertEquals(changedBalance, result)
    }

    @Test
    fun `잔고 사용에 성공한다`() {
        // Given
        val userId = "user123"
        val transactionType = "PAY"
        val amount = 100L
        val command = ChangeBalanceCommand(userId, TransactionType.PAY, amount)

        val balanceService = mockk<BalanceService>()
        val currentDateTime = LocalDateTime.now()
        val changedBalance = Balance(1L, userId, 400L, currentDateTime, currentDateTime)
        every { balanceService.changeBalance(userId, amount, TransactionType.PAY, any()) } returns changedBalance

        val changeBalanceUseCase = ChangeBalanceUseCase(balanceService)

        // When
        val result = changeBalanceUseCase(command)

        // Then
        verify(exactly = 1) { balanceService.changeBalance(userId, amount, TransactionType.PAY, any()) }

        assertEquals(changedBalance, result)
    }
}