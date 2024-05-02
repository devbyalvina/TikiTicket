package com.tikiticket.tickets.booking.api.dto

import com.tikiticket.tickets.booking.domain.Booking
import com.tikiticket.tickets.booking.domain.BookingStatusType
import java.time.LocalDateTime

data class MakeBookingResponse(
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
        fun of(booking: Booking): MakeBookingResponse {
            return MakeBookingResponse(
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
