package com.tikiticket.tickets.aggregate.balance.domain

import java.time.LocalDateTime

data class BalanceHistory(
    val balanceHistoryId: Long,
    val balanceId: Long,
    val userId: String,
    val transactionType: TransactionType,
    val balanceAmount: Long,
    val createdAt: LocalDateTime,
)