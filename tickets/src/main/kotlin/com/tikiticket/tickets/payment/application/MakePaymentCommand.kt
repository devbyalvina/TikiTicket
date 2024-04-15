package com.tikiticket.tickets.payment.application

import com.tikiticket.tickets.payment.application.exception.PaymentError
import com.tikiticket.tickets.payment.application.exception.PaymentException
import com.tikiticket.tickets.payment.domain.PaymentMethodType
import java.util.*

data class MakePaymentCommand (
    val bookingId: Long,
    val paymentMethod: String,
    val payerId: String
) {
    /**
     *  PaymentMethodType Enum으로 변환
     */
    fun getPaymentMethodTypeFromString(): PaymentMethodType {
        return kotlin.runCatching {
            PaymentMethodType.valueOf(paymentMethod.uppercase(Locale.getDefault()))
        }.getOrElse {
            throw PaymentException(PaymentError.WRONG_PAYMENT_METHOD)
        }
    }
}