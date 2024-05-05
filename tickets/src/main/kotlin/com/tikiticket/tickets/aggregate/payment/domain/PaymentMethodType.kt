package com.tikiticket.tickets.aggregate.payment.domain

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

/**
 * 결제 방법
 * - CARD : 카드
 * - BANK_TRANSFER : 계좌이체
 * - BALANCE: 잔고
 */
enum class PaymentMethodType {
    CARD, BANK_TRANSFER, BALANCE;

    @JsonValue
    fun toJson(): String {
        return this.name
    }

    companion object {
        @JsonCreator
        @JvmStatic
        fun fromJson(jsonValue: String): com.tikiticket.tickets.aggregate.payment.domain.PaymentMethodType {
            return com.tikiticket.tickets.aggregate.payment.domain.PaymentMethodType.valueOf(jsonValue)
        }
    }
}