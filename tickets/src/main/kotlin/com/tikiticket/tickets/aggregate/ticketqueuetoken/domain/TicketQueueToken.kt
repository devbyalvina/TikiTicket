package com.tikiticket.tickets.aggregate.ticketqueuetoken.domain

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import java.time.LocalDateTime

data class TicketQueueToken(
    val id: Long,
    val userId: String,
    val tokenStatus: TokenStatusType,
    val expiryDateTime: LocalDateTime,
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
    WAITING, ACTIVE, INACTIVE;

    @JsonValue
    fun toJson(): String {
        return this.name
    }

    companion object {
        @JsonCreator
        @JvmStatic
        fun fromJson(jsonValue: String): TokenStatusType {
            return valueOf(jsonValue)
        }
    }
}