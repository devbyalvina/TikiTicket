package com.tikiticket.tickets.balance.application

import com.tikiticket.tickets.appcore.domain.exception.CustomException
import org.springframework.boot.logging.LogLevel
import com.tikiticket.tickets.balance.domain.BalanceError
import com.tikiticket.tickets.balance.domain.TransactionType
import java.util.*

data class ChangeBalanceCommand(
    val userId: String,
    val transactionType: String,
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

    /**
     *  TransactionType Enum으로 변환
     */
    fun getTransactionTypeFromString(): TransactionType {
        return kotlin.runCatching {
            TransactionType.valueOf(transactionType.uppercase(Locale.getDefault()))
        }.getOrElse {
            throw CustomException(LogLevel.WARN, BalanceError.WRONG_TRANSACTION_TYPE)
        }
    }
}
