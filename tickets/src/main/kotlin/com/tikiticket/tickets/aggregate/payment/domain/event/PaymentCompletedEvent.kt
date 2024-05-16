package com.tikiticket.tickets.aggregate.payment.domain.event

/**
 * 결제 완료 이벤트
 */
data class PaymentCompletedEvent (
    val paymentId: Long,
    val concertId: Long,
    val concertSeatId: Long,
    val userId: String,
)