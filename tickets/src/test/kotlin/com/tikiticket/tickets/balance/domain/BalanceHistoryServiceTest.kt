package com.tikiticket.tickets.balance.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.time.LocalDateTime

class BalanceHistoryServiceTest {
    // Mock Repository 생성
    private val balanceHistoryRepository: BalanceHistoryRepository = mock(BalanceHistoryRepository::class.java)

    // 테스트 대상 서비스
    private val balanceHistoryService = BalanceHistoryService(balanceHistoryRepository)

    @Test
    fun `잔고 이력을 저장한다`() {
        // Given
        val balanceHistory = BalanceHistory("user123", 1L, 1000L, LocalDateTime.now())

        // When
        `when`(balanceHistoryRepository.save(balanceHistory)).thenReturn(balanceHistory)

        balanceHistoryService.storeBalanceHistory(balanceHistory)

        // Then
        // 저장된 balanceHistory가 기대한대로 저장되었는지 확인
        val savedBalanceHistory = balanceHistoryRepository.save(balanceHistory)
        assertEquals(balanceHistory, savedBalanceHistory)
    }
}