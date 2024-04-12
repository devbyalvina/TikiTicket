package com.tikiticket.tickets.booking.domain

import java.time.LocalDateTime

data class Booking (
    val id: Long,
    val bookingStatus: BookingStatusType,
    val expiryDateTime: LocalDateTime,
    val concertId: Long,
    val concertName: String,
    val artistName: String,
    val concertDate: LocalDateTime,
    val venue: String,
    val searNo: Long,
    val ticketPrice: Long,
    val bookedAt: LocalDateTime,
    val paidAt: LocalDateTime?,
    val expiredAt: LocalDateTime?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
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