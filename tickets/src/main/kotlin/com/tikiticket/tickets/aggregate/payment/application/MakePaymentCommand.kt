package com.tikiticket.tickets.aggregate.payment.application

data class MakePaymentCommand (
    val bookingId: Long,
    val paymentMethod: com.tikiticket.tickets.aggregate.payment.domain.PaymentMethodType,
    val payerId: String
)