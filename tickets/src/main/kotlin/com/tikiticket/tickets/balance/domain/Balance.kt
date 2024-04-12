package com.tikiticket.tickets.balance.domain

import java.time.LocalDateTime

data class Balance(
    val userId: String,
    val balanceAmount: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)