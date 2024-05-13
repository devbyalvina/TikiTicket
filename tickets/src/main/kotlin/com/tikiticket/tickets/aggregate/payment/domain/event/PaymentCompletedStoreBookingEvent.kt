package com.tikiticket.tickets.aggregate.payment.domain.event

data class PaymentCompletedStoreBookingEvent(
    val paymentId: Long,
    val concertId: Long,
    val concertSeatId: Long,
    val userId: String,
)
