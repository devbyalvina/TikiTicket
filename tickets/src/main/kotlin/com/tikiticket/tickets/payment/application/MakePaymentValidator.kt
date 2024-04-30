package com.tikiticket.tickets.payment.application

import com.tikiticket.tickets.appcore.domain.exception.CustomException
import org.springframework.boot.logging.LogLevel
import com.tikiticket.tickets.payment.domain.PaymentError

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