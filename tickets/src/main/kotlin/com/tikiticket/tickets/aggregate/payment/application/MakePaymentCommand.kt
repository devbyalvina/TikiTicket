package com.tikiticket.tickets.aggregate.payment.application

import com.tikiticket.tickets.aggregate.payment.domain.PaymentMethodType

data class MakePaymentCommand (
    val bookingId: Long,
    val paymentMethod: PaymentMethodType,
    val payerId: String
)