package com.tikiticket.tickets.balance.application

import com.tikiticket.tickets.balance.application.exception.BalanceError
import com.tikiticket.tickets.balance.application.exception.BalanceException

class ChangeBalanceValidator {
    /**
     *  계산 금액 체크
     */
    companion object {
        fun checkCalculatedAmount(calculatedAmount: Long) {
            require(calculatedAmount > 0) {
                throw BalanceException(BalanceError.INSUFFICIENT_BALANCE)
            }
        }
    }
}