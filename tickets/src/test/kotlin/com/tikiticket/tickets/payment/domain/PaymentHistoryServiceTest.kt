package com.tikiticket.tickets.payment.domain

class PaymentHistoryServiceTest {
//    // Mock Repository 생성
//    private val paymentHistoryRepository: PaymentHistoryRepository = mock(PaymentHistoryRepository::class.java)
//
//    // 테스트 대상 서비스
//    private val paymentHistoryService = PaymentHistoryService(paymentHistoryRepository)
//
//    @Test
//    fun `결제 이력을 저장한다`() {
//        // Given
//        val paymentHistory = PaymentHistory(
//            1L,
//            123L,
//            456L,
//            PaymentMethodType.BALANCE,
//            10000,
//            "user123",
//            LocalDateTime.now(),
//            PaymentStatus.SUCCESS,
//            LocalDateTime.now()
//        )
//
//        // When
//        `when`(paymentHistoryRepository.save(paymentHistory)).thenReturn(paymentHistory)
//
//        val savedPaymentHistory = paymentHistoryService.storePaymentHistory(paymentHistory)
//
//        // Then
//        assertEquals(paymentHistory, savedPaymentHistory)
//    }
}