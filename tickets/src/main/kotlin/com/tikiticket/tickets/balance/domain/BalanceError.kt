package com.tikiticket.tickets.balance.domain

import com.tikiticket.tickets.appcore.domain.exception.CustomError

enum class BalanceError (
    override val message: String,
): CustomError {
    /**
     *  BALANCE_NOT_FOUND
     *  - API.9 잔고 조회
     */
    BALANCE_NOT_FOUND("The user's balance does not exist."),
    /**
     *  WRONG_TRANSACTION_TYPE
     *  - API.10 잔고 변경
     */
    WRONG_TRANSACTION_TYPE("No such transaction type."),

    /**
     *  INVALID_AMOUNT_PARAMETER
     *  - API.10 잔고 변경
     */
    INVALID_AMOUNT_PARAMETER("Amount must be positive."),
    /**
     *  INSUFFICIENT_BALANCE
     *  - API.10 잔고 변경
     */
    INSUFFICIENT_BALANCE("The user does not have enough balance to complete the transaction.");

    override val errorCode: String = name
}