package com.tikiticket.tickets.balance.domain

import java.time.LocalDateTime

data class Balance(
    val userId: String,
    val balanceAmount: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    fun calculateChangedBalance(transactionType: TransactionType, amount: Long) : Long {
        val calculatedAmount = when(transactionType) {
            TransactionType.PAY -> balanceAmount - amount
            TransactionType.CHARGE -> balanceAmount + amount
        }
        return calculatedAmount
    }
}
