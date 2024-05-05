package com.tikiticket.tickets.aggregate.payment.application

import com.tikiticket.tickets.aggregate.payment.domain.PaymentError
import com.tikiticket.tickets.global.domain.exception.CustomException
import org.springframework.boot.logging.LogLevel

class MakePaymentValidator {
    /**
     *  계산 금액 체크
     */
    companion object {
        fun checkCalculatedAmount(calculatedAmount: Long) {
            require(calculatedAmount > 0) {
                throw CustomException(LogLevel.INFO, PaymentError.INSUFFICIENT_BALANCE)
            }
        }
    }
}