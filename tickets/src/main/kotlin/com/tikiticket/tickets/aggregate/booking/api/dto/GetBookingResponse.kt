package com.tikiticket.tickets.aggregate.booking.api.dto

import com.tikiticket.tickets.aggregate.booking.domain.Booking
import com.tikiticket.tickets.aggregate.booking.domain.BookingStatusType
import java.time.LocalDateTime

data class GetBookingResponse (
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
) {
    companion object {
        fun of(booking: Booking): GetBookingResponse {
            return GetBookingResponse(
                booking.id,
                booking.bookerId,
                booking.bookingStatus,
                booking.expiryDateTime,
                booking.concertId,
                booking.concertName,
                booking.artistName,
                booking.concertDate,
                booking.venue,
                booking.seatId,
                booking.seatNo,
                booking.ticketPrice,
            )
        }
    }
}