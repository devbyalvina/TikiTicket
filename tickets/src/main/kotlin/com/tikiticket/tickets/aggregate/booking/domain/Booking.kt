package com.tikiticket.tickets.aggregate.booking.domain

import java.time.LocalDateTime

data class Booking (
    val id: Long,
    val bookerId: String,
    val bookingStatus: BookingStatusType,
    val expiryDateTime: LocalDateTime,
    val concertId: Long,
    val concertName: String,
    val artistName: String,
    val concertDate: LocalDateTime,
    val venue: String,
    val seatId: Long,
    val seatNo: Long,
    val ticketPrice: Long,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
)

/**
 * 좌석 상태
 * - BOOKED : 예약
 * - PAID : 결제완료
 * - EXPIRED : 만료
 */
enum class BookingStatusType {
    BOOKED, PAID, EXPIRED
}