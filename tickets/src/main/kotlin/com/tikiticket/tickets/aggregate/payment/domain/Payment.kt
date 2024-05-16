package com.tikiticket.tickets.aggregate.payment.domain

import java.time.LocalDateTime

data class Payment(
    val id: Long,
    val paymentMethod: PaymentMethodType,
    val paymentAmount: Long,
    val payerId: String,
    val paymentDateTime: LocalDateTime,
    val paymentStatus: PaymentStatusType,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)