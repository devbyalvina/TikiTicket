package com.tikiticket.tickets.payment.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.time.LocalDateTime

class PaymentServiceTest {
    // Mock Repository 생성
    private val paymentRepository: PaymentRepository = mock(PaymentRepository::class.java)

    // 테스트 대상 서비스
    private val paymentService = PaymentService(paymentRepository)

    @Test
    fun `결제한다`() {
        // Given
        val payment = Payment(
            1L,
            123L,
            PaymentMethodType.BALANCE,
            "user123",
            LocalDateTime.now(),
            PaymentStatus.FAILED,
            LocalDateTime.now(),
            LocalDateTime.now()
        )

        // When
        `when`(paymentRepository.savePayment(payment)).thenReturn(payment)

        val savedPayment = paymentService.makePayment(payment)

        // Then
        assertEquals(payment, savedPayment)
    }

    @Test
    fun `결제 내역을 조회한다`() {
        // Given
        val paymentId = 1L
        val payment = Payment(
            paymentId,
            123L,
            PaymentMethodType.BALANCE,
            "user123",
            LocalDateTime.now(),
            PaymentStatus.SUCCESS,
            LocalDateTime.now(),
            LocalDateTime.now()
        )

        // When
        `when`(paymentRepository.findPaymentById(paymentId)).thenReturn(payment)

        val foundPayment = paymentService.findPayment(paymentId)

        // Then
        assertEquals(payment, foundPayment)
    }
}