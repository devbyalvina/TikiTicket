package com.tikiticket.tickets.ticketqueuetoken.domain

import java.time.LocalDateTime

data class TicketQueueToken(
    val id: Long,
    val userId: String,
    val tokenStatus: TokenStatusType,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)

/**
 * 좌석 상태
 * - WAITING : 대기
 * - ACTIVE : 활성
 * - INACTIVE : 비활성
 */
enum class TokenStatusType {
    WAITING, ACTIVE, INACTIVE
}