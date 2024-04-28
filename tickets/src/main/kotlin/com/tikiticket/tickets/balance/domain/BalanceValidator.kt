package com.tikiticket.tickets.balance.domain

import com.tikiticket.tickets.appcore.domain.exception.CustomException
import com.tikiticket.tickets.appcore.domain.log.LogLevel

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