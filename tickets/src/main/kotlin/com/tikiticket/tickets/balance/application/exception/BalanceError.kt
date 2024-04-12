package com.tikiticket.tickets.balance.application.exception

enum class BalanceError (
    val message: String,
) {
    /**
     *  BALANCE_NOT_FOUND
     *  - API.9 잔고 조회
     */
    BALANCE_NOT_FOUND("The user's balance does not exist."),
}