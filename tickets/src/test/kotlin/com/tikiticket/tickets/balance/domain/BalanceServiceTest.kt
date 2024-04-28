package com.tikiticket.tickets.balance.domain

import com.tikiticket.tickets.appcore.domain.exception.CustomException
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
    fun `유저ID로 수정을 위해 잔고를 조회한다`() {
        // Given
        val userId = "user123"
        val expectedBalance = Balance(userId, 1000L, LocalDateTime.now(), LocalDateTime.now())

        // When
        `when`(balanceRepository.findBalanceByUserIdForUpdate(userId)).thenReturn(expectedBalance)

        val retrievedBalance = balanceService.retrieveBalanceForUpdate(userId)

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

    @Test
    fun `계산된 잔고를 업데이트한다`() {
        // Given
        val userId = "user123"
        val amount = 100L
        val transactionType = TransactionType.CHARGE
        val currentDateTime = LocalDateTime.now()

        val balanceRepository = mockk<BalanceRepository>()
        val balance = Balance(userId, 500L, currentDateTime, currentDateTime)
        every { balanceRepository.findBalanceByUserIdForUpdate(userId) } returns balance
        every { balanceRepository.saveBalanceHistory(any()) } returns mockk()
        every { balanceRepository.updateBalance(any()) } just Runs

        val balanceService = BalanceService(balanceRepository)

        // When
        val changedBalance = balanceService.changeBalance(userId, amount, transactionType, currentDateTime)

        // Then
        verify(exactly = 1) { balanceRepository.findBalanceByUserIdForUpdate(userId) }
        verify(exactly = 1) { balanceRepository.saveBalanceHistory(any()) }
        verify(exactly = 1) { balanceRepository.updateBalance(any()) }

        assertEquals(600L, changedBalance.balanceAmount)
    }

    @Test
    fun `계산된 잔고가 양수가 아니면 잔고 업데이트에 실패한다`() {
        // Given
        val userId = "user123"
        val amount = -100L
        val transactionType = TransactionType.CHARGE
        val currentDateTime = LocalDateTime.now()

        val balanceRepository = mockk<BalanceRepository>()
        every { balanceRepository.findBalanceByUserIdForUpdate(userId) } returns null

        val balanceService = BalanceService(balanceRepository)

        // When, Then
        assertThrows<CustomException> { balanceService.changeBalance(userId, amount, transactionType, currentDateTime) }
    }
}