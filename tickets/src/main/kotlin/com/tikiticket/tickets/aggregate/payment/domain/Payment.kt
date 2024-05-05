package com.tikiticket.tickets.aggregate.payment.domain

import java.time.LocalDateTime

data class Payment(
    val id: Long,
    val bookingId: Long,
    val paymentMethod: com.tikiticket.tickets.aggregate.payment.domain.PaymentMethodType,
    val paymentAmount: Long,
    val payerId: String,
    val paymentDateTime: LocalDateTime,
    val paymentStatus: com.tikiticket.tickets.aggregate.payment.domain.PaymentStatusType,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)