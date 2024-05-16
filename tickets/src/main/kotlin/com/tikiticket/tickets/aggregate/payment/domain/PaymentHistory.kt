package com.tikiticket.tickets.aggregate.payment.domain

import java.time.LocalDateTime

data class PaymentHistory(
    val paymentId: Long,
    val paymentHistoryId: Long,
    val paymentMethod: PaymentMethodType,
    val paymentAmount: Long,
    val payerId: String,
    val paymentDateTime: LocalDateTime,
    val paymentStatus: PaymentStatusType,
    val createdAt: LocalDateTime,
)

