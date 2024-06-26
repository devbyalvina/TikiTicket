package com.tikiticket.tickets.aggregate.balance.domain

import com.tikiticket.tickets.global.domain.exception.CustomException
import org.springframework.boot.logging.LogLevel

class BalanceValidator {
    /**
     *  계산 금액 체크
     */
    companion object {
        fun checkCalculatedAmount(calculatedAmount: Long) {
            require(calculatedAmount > 0) {
                throw CustomException(LogLevel.INFO, BalanceError.INSUFFICIENT_BALANCE)
            }
        }
    }
}