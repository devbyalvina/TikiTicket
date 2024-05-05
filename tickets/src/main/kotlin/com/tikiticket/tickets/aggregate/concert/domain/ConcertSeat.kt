package com.tikiticket.tickets.aggregate.concert.domain

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import java.time.LocalDateTime

data class ConcertSeat (
    val id: Long,
    val concertId: Long,
    val seatNo: Long,
    val seatStatus: SeatStatusType,
    val ticketPrice: Long,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)

/**
 * 좌석 상태
 * - AVAILABLE : 예매 가능
 * - BOOKED : 예약
 * - PAID : 결제완료
 */
enum class SeatStatusType {
    AVAILABLE, BOOKED, PAID;

    @JsonValue
    fun toJson(): String {
        return this.name
    }

    companion object {
        @JsonCreator
        @JvmStatic
        fun fromJson(jsonValue: String): SeatStatusType {
            return valueOf(jsonValue)
        }
    }
}