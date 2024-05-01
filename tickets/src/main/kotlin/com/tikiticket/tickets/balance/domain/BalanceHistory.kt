package com.tikiticket.tickets.balance.domain

import java.time.LocalDateTime

data class BalanceHistory(
    val balanceHistoryId: Long,
    val userId: String,
    val transactionType: TransactionType,
    val balanceAmount: Long,
    val createdAt: LocalDateTime,
)