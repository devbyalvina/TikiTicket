package com.tikiticket.tickets.balance.application

import com.tikiticket.tickets.appcore.application.exception.CustomException
import com.tikiticket.tickets.appcore.application.log.LogLevel
import com.tikiticket.tickets.balance.domain.BalanceError

class ChangeBalanceValidator {
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