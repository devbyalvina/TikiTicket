package com.tikiticket.tickets.balance.domain

import java.time.LocalDateTime

data class BalanceHistory(
    val userId: String,
    val balanceHistoryId: Long,
    val balanceAmount: Long,
    val createdAt: LocalDateTime,
)

/**
 * 거래 유형
 * - CHARGE : 충전
 * - PAY : 결제
 */
enum class TransactionType {
    CHARGE, PAY
}