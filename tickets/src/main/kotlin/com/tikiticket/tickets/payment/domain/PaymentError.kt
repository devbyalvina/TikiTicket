package com.tikiticket.tickets.payment.domain

import com.tikiticket.tickets.appcore.domain.exception.CustomError

enum class PaymentError(
    override val message: String,
): CustomError {
    /**
     *  WRONG_PAYMENT_METHOD
     *  - API.8 예매 내역 결제
     */
    WRONG_PAYMENT_METHOD("The selected payment method can not be used."),
    /**
     *  BOOKING_NOT_FOUND
     *  - API.8 예매 내역 결제
     */
    BOOKING_NOT_FOUND("The specified booking was not found."),
    /**
     *  INSUFFICIENT_BALANCE
     *  - API.10 잔고 변경
     */
    INSUFFICIENT_BALANCE("The user does not have enough balance to complete the transaction.");

    override val errorCode: String = name
}