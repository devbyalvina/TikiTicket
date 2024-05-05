package com.tikiticket.tickets.aggregate.payment.api.dto

import com.tikiticket.tickets.aggregate.payment.domain.PaymentMethodType

data class MakePaymentRequest(
    val paymentMethod: com.tikiticket.tickets.aggregate.payment.domain.PaymentMethodType
)
