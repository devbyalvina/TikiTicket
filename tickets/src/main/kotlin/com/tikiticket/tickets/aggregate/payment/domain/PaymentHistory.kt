package com.tikiticket.tickets.aggregate.payment.domain

import java.time.LocalDateTime

data class PaymentHistory(
    val paymentId: Long,
    val paymentHistoryId: Long,
    val bookingId: Long,
    val paymentMethod: com.tikiticket.tickets.aggregate.payment.domain.PaymentMethodType,
    val paymentAmount: Long,
    val payerId: String,
    val paymentDateTime: LocalDateTime,
    val paymentStatus: com.tikiticket.tickets.aggregate.payment.domain.PaymentStatusType,
    val createdAt: LocalDateTime,
)

