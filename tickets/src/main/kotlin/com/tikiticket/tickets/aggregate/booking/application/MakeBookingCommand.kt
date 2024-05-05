package com.tikiticket.tickets.aggregate.booking.application

data class MakeBookingCommand (
    val userId: String,
    val concertId: Long,
    val concertSeatId: Long,
)