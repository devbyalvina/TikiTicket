package com.tikiticket.tickets.balance.domain

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

/**
 * 거래 유형
 * - CHARGE : 충전
 * - PAY : 결제
 */
enum class TransactionType {
    CHARGE, PAY;

    @JsonValue
    fun toJson(): String {
        return this.name
    }

    companion object {
        @JsonCreator
        @JvmStatic
        fun fromJson(jsonValue: String): TransactionType {
            return TransactionType.valueOf(jsonValue)
        }
    }
}