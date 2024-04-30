package com.tikiticket.tickets.payment.application

import com.tikiticket.tickets.appcore.domain.exception.CustomException
import org.springframework.boot.logging.LogLevel
import com.tikiticket.tickets.payment.domain.PaymentError
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
            throw CustomException(LogLevel.WARN, PaymentError.WRONG_PAYMENT_METHOD)
        }
    }
}