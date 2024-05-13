package com.tikiticket.tickets.aggregate.payment.domain.event

data class PaymentCompletedChangeConcertEvent (
    val paymentId: Long,
    val concertId: Long,
    val concertSeatId: Long,
)
