package com.tikiticket.tickets.balance.application.exception

class BalanceException: RuntimeException {
    var error: BalanceError
    var errorMessage: String

    constructor(balanceError: BalanceError) : super(balanceError.message) {
        this.error = balanceError
        this.errorMessage = balanceError.message
    }

    constructor(balanceError: BalanceError, message: String) : super(message) {
        this.error = balanceError
        this.errorMessage = message
    }
}