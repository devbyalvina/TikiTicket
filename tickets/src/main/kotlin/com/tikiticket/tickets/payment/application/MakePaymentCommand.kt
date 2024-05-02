package com.tikiticket.tickets.payment.application

import com.tikiticket.tickets.payment.domain.PaymentMethodType

data class MakePaymentCommand (
    val bookingId: Long,
    val paymentMethod: PaymentMethodType,
    val payerId: String
)