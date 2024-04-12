package com.tikiticket.tickets.payment.domain

import java.time.LocalDateTime

data class PaymentHistory(
    val paymentId: Long,
    val paymentHistoryId: Long,
    val bookingId: Long,
    val paymentMethod: PaymentMethod,
    val payerId: Long,
    val paymentDateTime: LocalDateTime,
    val paymentStatus: PaymentStatus,
    val createdAt: LocalDateTime,
)

