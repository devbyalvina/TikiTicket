package com.tikiticket.tickets.balance.domain

import java.time.LocalDateTime

data class BalanceHistory(
    val userId: String,
    val balanceHistoryId: Long,
    val balanceAmount: Long,
    val createdAt: LocalDateTime,
)