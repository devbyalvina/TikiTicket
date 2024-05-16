package com.tikiticket.tickets.aggregate.payment.application

import com.tikiticket.tickets.aggregate.payment.domain.PaymentMethodType

data class MakePaymentWithEventCommand (
    val paymentMethod: PaymentMethodType,
    val payerId: String,
    val concertId: Long,
    val concertSeatId: Long,
    val ticketPrice: Long,
)