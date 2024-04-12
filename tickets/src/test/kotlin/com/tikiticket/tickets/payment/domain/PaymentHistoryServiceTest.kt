package com.tikiticket.tickets.payment.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.time.LocalDateTime

class PaymentHistoryServiceTest {
    // Mock Repository 생성
    private val paymentHistoryRepository: PaymentHistoryRepository = mock(PaymentHistoryRepository::class.java)

    // 테스트 대상 서비스
    private val paymentHistoryService = PaymentHistoryService(paymentHistoryRepository)

    @Test
    fun `결제 이력을 저장한다`() {
        // Given
        val paymentHistory = PaymentHistory(
            1L,
            123L,
            456L,
            PaymentMethod.BALANCE,
            789L,
            LocalDateTime.now(),
            PaymentStatus.SUCCESS,
            LocalDateTime.now()
        )

        // When
        `when`(paymentHistoryRepository.save(paymentHistory)).thenReturn(paymentHistory)

        val savedPaymentHistory = paymentHistoryService.storePaymentHistory(paymentHistory)

        // Then
        assertEquals(paymentHistory, savedPaymentHistory)
    }
}