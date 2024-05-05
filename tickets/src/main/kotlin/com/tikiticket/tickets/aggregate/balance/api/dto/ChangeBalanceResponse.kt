package com.tikiticket.tickets.aggregate.balance.api.dto

import com.tikiticket.tickets.aggregate.balance.domain.Balance
import java.time.LocalDateTime

data class ChangeBalanceResponse(
    val userId: String,
    val balanceAmount: Long,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun of(balance: Balance): ChangeBalanceResponse {
            return ChangeBalanceResponse (
                userId = balance.userId,
                balanceAmount = balance.balanceAmount,
                updatedAt = balance.updatedAt!!
            )
        }
    }
}
