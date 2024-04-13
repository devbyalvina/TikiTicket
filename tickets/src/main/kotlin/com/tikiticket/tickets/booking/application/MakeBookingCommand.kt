package com.tikiticket.tickets.booking.application

data class MakeBookingCommand (
    val userId: String,
    val concertId: Long,
    val seatNo: Long
) {
}