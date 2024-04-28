package com.tikiticket.tickets.booking.application

data class MakeBookingCommand (
    val userId: String,
    val concertSeatId: Long,
    val concertId: Long,
) {
}