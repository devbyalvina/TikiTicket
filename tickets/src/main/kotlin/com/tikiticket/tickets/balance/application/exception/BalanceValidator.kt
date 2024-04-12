package com.tikiticket.tickets.balance.application.exception

class BalanceValidator {
    /**
     *  계산 금액 체크
     */
    companion object {
        fun checkCalculatedAmount(calculatedAmount: Long) {
            if (calculatedAmount <= 0) {
                throw BalanceException(BalanceError.INSUFFICIENT_BALANCE)
            }
        }
    }
}