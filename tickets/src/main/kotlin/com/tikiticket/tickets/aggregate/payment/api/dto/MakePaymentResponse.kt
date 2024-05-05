package com.tikiticket.tickets.aggregate.payment.api.dto

import com.tikiticket.tickets.aggregate.payment.domain.Payment
import com.tikiticket.tickets.aggregate.payment.domain.PaymentMethodType
import com.tikiticket.tickets.aggregate.payment.domain.PaymentStatusType
import java.time.LocalDateTime

data class MakePaymentResponse(
    val paymentId: Long,
    val bookingId: Long,
    val paymentMethod: com.tikiticket.tickets.aggregate.payment.domain.PaymentMethodType,
    val paymentAmount: Long,
    val payerId: String,
    val paymentDateTime: LocalDateTime,
    val paymentStatus: com.tikiticket.tickets.aggregate.payment.domain.PaymentStatusType,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun of (payment: com.tikiticket.tickets.aggregate.payment.domain.Payment): com.tikiticket.tickets.aggregate.payment.api.dto.MakePaymentResponse {
            return com.tikiticket.tickets.aggregate.payment.api.dto.MakePaymentResponse(
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
