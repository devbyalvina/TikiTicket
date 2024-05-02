package com.tikiticket.tickets.payment.api.dto

import com.tikiticket.tickets.payment.domain.PaymentMethodType

data class MakePaymentRequest(
    val paymentMethod: PaymentMethodType
)
