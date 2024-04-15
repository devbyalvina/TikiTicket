package com.tikiticket.tickets.payment.application

import com.tikiticket.tickets.payment.application.exception.PaymentError
import com.tikiticket.tickets.payment.application.exception.PaymentException

class MakePaymentValidator {
    /**
     *  계산 금액 체크
     */
    companion object {
        fun checkCalculatedAmount(calculatedAmount: Long) {
            require(calculatedAmount > 0) {
                throw PaymentException(PaymentError.INSUFFICIENT_BALANCE)
            }
        }
    }
}