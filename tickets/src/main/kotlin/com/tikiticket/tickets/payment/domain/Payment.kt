package com.tikiticket.tickets.payment.domain

import java.time.LocalDateTime

data class Payment(
    val id: Long,
    val bookingId: Long,
    val paymentMethod: PaymentMethodType,
    val payerId: Long,
    val paymentDateTime: LocalDateTime,
    val paymentStatus: PaymentStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

enum class PaymentMethodType {
    CARD, BANK_TRANSFER, BALANCE
}

enum class PaymentStatus {
    PENDING, SUCCESS, FAILED
}