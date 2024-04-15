package com.tikiticket.tickets.payment.domain

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
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
            10000,
            "user123",
            LocalDateTime.now(),
            PaymentStatus.FAILED,
            LocalDateTime.now(),
            LocalDateTime.now()
        )

        // When
        `when`(paymentRepository.savePayment(payment)).thenReturn(payment)

        val savedPayment = paymentService.storePayment(payment)

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
            10000,
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

    @Test
    fun `결제와 결제 이력 저장에 성공한다`() {
        // Given
        val bookingId = 1L
        val paymentMethod = PaymentMethodType.BALANCE
        val payerId = "user123"
        val paymentAmount = 10000L
        val currentDateTime = LocalDateTime.now()

        // 모의 객체 생성
        val paymentRepository = mockk<PaymentRepository>()

        val payment = Payment(
            id = 0,
            bookingId = bookingId,
            paymentMethod = paymentMethod,
            paymentAmount = paymentAmount,
            payerId = payerId,
            paymentDateTime = currentDateTime,
            paymentStatus = PaymentStatus.SUCCESS,
            createdAt = currentDateTime,
            updatedAt = currentDateTime
        )
        every { paymentRepository.savePayment(any()) } returns payment

        val paymentHistory = PaymentHistory(
            paymentId = payment.id,
            paymentHistoryId = 0,
            bookingId = bookingId,
            paymentMethod = paymentMethod,
            paymentAmount = paymentAmount,
            payerId = payerId,
            paymentDateTime = currentDateTime,
            paymentStatus = PaymentStatus.SUCCESS,
            createdAt = currentDateTime
        )
        every { paymentRepository.savePaymentHistory(any()) } returns paymentHistory

        // 테스트 대상 서비스에 모의 객체 주입
        val paymentService = PaymentService(paymentRepository)

        // When
        val result = paymentService.makePayment(bookingId, paymentMethod, payerId, paymentAmount, currentDateTime)

        // Then
        assertEquals(payment, result)
        verify(exactly = 1) { paymentRepository.savePayment(payment) }
        verify(exactly = 1) { paymentRepository.savePaymentHistory(paymentHistory) }
    }
}