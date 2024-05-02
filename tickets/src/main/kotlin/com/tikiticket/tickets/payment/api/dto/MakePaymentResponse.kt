package com.tikiticket.tickets.payment.api.dto

import com.tikiticket.tickets.payment.domain.Payment
import com.tikiticket.tickets.payment.domain.PaymentMethodType
import com.tikiticket.tickets.payment.domain.PaymentStatusType
import java.time.LocalDateTime

data class MakePaymentResponse(
    val paymentId: Long,
    val bookingId: Long,
    val paymentMethod: PaymentMethodType,
    val paymentAmount: Long,
    val payerId: String,
    val paymentDateTime: LocalDateTime,
    val paymentStatus: PaymentStatusType,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun of (payment: Payment): MakePaymentResponse {
            return MakePaymentResponse (
                payment.id,
                payment.bookingId,
                payment.paymentMethod,
                payment.paymentAmount,
                payment.payerId,
                payment.paymentDateTime,
                payment.paymentStatus,
                payment.updatedAt,
            )
        }
    }
}
