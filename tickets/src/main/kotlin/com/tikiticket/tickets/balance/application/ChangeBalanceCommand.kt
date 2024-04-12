package com.tikiticket.tickets.balance.application

import com.tikiticket.tickets.balance.application.exception.BalanceError
import com.tikiticket.tickets.balance.application.exception.BalanceException
import com.tikiticket.tickets.balance.domain.TransactionType
import java.util.*

data class ChangeBalanceCommand(
    val userId: String,
    val transactionType: String,
    val amount: Long
) {
    fun validate() {
        checkPositive()
    }

    /**
     *  amount 양수 여부 체크
     */
    fun checkPositive() {
        if (amount <= 0) {
            throw BalanceException(BalanceError.INVALID_AMOUNT_PARAMETER)
        }
    }

    /**
     *  TransactionType Enum으로 변환
     */
    fun getTransactionTypeFromString(): TransactionType {
        return kotlin.runCatching {
            TransactionType.valueOf(transactionType.uppercase(Locale.getDefault()))
        }.getOrElse {
            throw BalanceException(BalanceError.WRONG_TRANSACTION_TYPE)
        }
    }
}
