package com.tikiticket.tickets.payment.application.exception

class PaymentException: RuntimeException {
    var error: PaymentError
    var errorMessage: String

    constructor(paymentError: PaymentError) : super(paymentError.message) {
        this.error = paymentError
        this.errorMessage = paymentError.message
    }

    constructor(paymentError: PaymentError, message: String) : super(message) {
        this.error = paymentError
        this.errorMessage = message
    }
}