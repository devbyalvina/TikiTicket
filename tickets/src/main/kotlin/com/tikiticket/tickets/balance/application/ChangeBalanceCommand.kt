package com.tikiticket.tickets.balance.application

import com.tikiticket.tickets.appcore.domain.exception.CustomException
import com.tikiticket.tickets.balance.domain.BalanceError
import com.tikiticket.tickets.balance.domain.TransactionType
import org.springframework.boot.logging.LogLevel

data class ChangeBalanceCommand(
    val userId: String,
    val transactionType: TransactionType,
    val amount: Long
) {
    init {
        validate()
    }

    fun validate() {
        checkPositive()
    }

    /**
     *  amount 양수 여부 체크
     */
    fun checkPositive() {
        require (amount > 0) {
            throw CustomException(LogLevel.WARN, BalanceError.INVALID_AMOUNT_PARAMETER)
        }
    }
}
