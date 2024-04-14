package com.tikiticket.tickets.balance.domain

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.time.LocalDateTime

class BalanceServiceTest {
    // Mock Repository 생성
    private val balanceRepository: BalanceRepository = mock(BalanceRepository::class.java)

    // 테스트 대상 서비스
    private val balanceService = BalanceService(balanceRepository)

    @Test
    fun `유저ID로 잔고를 조회한다`() {
        // Given
        val userId = "user123"
        val expectedBalance = Balance(userId, 1000L, LocalDateTime.now(), LocalDateTime.now())

        // When
        `when`(balanceRepository.findBalanceByUserId(userId)).thenReturn(expectedBalance)

        val retrievedBalance = balanceService.retrieveBalance(userId)

        // Then
        assertEquals(expectedBalance, retrievedBalance)
    }

    @Test
    fun `잔고를 저장한다`() {
        // Given
        val balance = Balance("user123", 1000L, LocalDateTime.now(), LocalDateTime.now())

        // When
        `when`(balanceRepository.saveBalance(balance)).thenReturn(balance)

        balanceService.storeBalance(balance)

        // Then
        // 저장된 balance가 기대한대로 저장되었는지 확인
        val savedBalance = balanceRepository.saveBalance(balance)
        assertEquals(balance, savedBalance)
    }

    @Test
    fun `잔고를 변경한다`() {
        // Given
        val balance = Balance("user123", 1000L, LocalDateTime.now(), LocalDateTime.now())

        // When
        balanceService.modifyBalance(balance)

        // Then
        Mockito.verify(balanceRepository, Mockito.times(1)).updateBalance(balance)
    }

    @Test
    fun `잔고 이력을 저장한다`() {
        // Given
        val balanceHistory = BalanceHistory("user123", 1L, 1000L, LocalDateTime.now())

        // When
        `when`(balanceRepository.saveBalanceHistory(balanceHistory)).thenReturn(balanceHistory)

        balanceService.storeBalanceHistory(balanceHistory)

        // Then
        // 저장된 balanceHistory가 기대한대로 저장되었는지 확인
        val savedBalanceHistory = balanceRepository.saveBalanceHistory(balanceHistory)
        assertEquals(balanceHistory, savedBalanceHistory)
    }

    @Test
    fun `잔고를 충전한다`() {
        // Given
        val userId = "user123"
        val initialBalanceAmount = 3000L
        val transactionType = TransactionType.CHARGE
        val changeAmount = 5000L
        val currentDateTime = LocalDateTime.now()

        val existingBalance = Balance(userId, initialBalanceAmount, currentDateTime, currentDateTime)

        val balanceRepository = mockk<BalanceRepository>()
        every { balanceRepository.saveBalanceHistory(any()) } returns mockk()
        every { balanceRepository.updateBalance(any()) } just Runs

        val balanceService = BalanceService(balanceRepository)

        // When
        val result = balanceService.changeBalance(existingBalance, transactionType, initialBalanceAmount + changeAmount, currentDateTime)

        // Then
        val expectedCalculatedAmount = initialBalanceAmount + changeAmount
        assertEquals(expectedCalculatedAmount, result.balanceAmount)
        verify(exactly = 1) { balanceRepository.updateBalance(result) }
        verify(exactly = 1) { balanceRepository.saveBalanceHistory(any()) }
    }

    @Test
    fun `잔고를 사용한다`() {
        // Given
        val userId = "user123"
        val initialBalanceAmount = 3000L
        val transactionType = TransactionType.PAY
        val changeAmount = 2000L
        val currentDateTime = LocalDateTime.now()

        val existingBalance = Balance(userId, initialBalanceAmount, currentDateTime, currentDateTime)

        val balanceRepository = mockk<BalanceRepository>()
        every { balanceRepository.saveBalanceHistory(any()) } returns mockk()
        every { balanceRepository.updateBalance(any()) } just Runs

        val balanceService = BalanceService(balanceRepository)

        // When
        val result = balanceService.changeBalance(existingBalance, transactionType, initialBalanceAmount - changeAmount, currentDateTime)

        // Then
        assertEquals(initialBalanceAmount - changeAmount, result.balanceAmount)
        verify(exactly = 1) { balanceRepository.saveBalanceHistory(any()) }
        verify(exactly = 1) { balanceRepository.updateBalance(any()) }
    }
}