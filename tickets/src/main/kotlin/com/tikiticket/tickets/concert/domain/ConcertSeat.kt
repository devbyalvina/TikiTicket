package com.tikiticket.tickets.concert.domain

import java.time.LocalDateTime

data class ConcertSeat (
    val concertId: Long,
    val seatNo: Long,
    val seatStatus: seatStatusType,
    val ticketPrice: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

/**
 * 좌석 상태
 * - AVAILABLE : 예매 가능
 * - BOOKED : 예약
 * - PAID : 결제완료
 */
enum class seatStatusType {
    AVAILABLE, BOOKED, PAID
}