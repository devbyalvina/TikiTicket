package com.tikiticket.tickets.booking.application.exception

enum class BookingError (
    val message: String,
) {
    /**
     *  CONCERT_NOT_FOUND
     *  - API.6 예매
     */
    CONCERT_NOT_FOUND("The specified concert was not found."),
    /**
     *  CONCERT_SEAT_NOT_FOUND
     *  - API.6 예매
     */
    CONCERT_SEAT_NOT_FOUND("The specified seat was not found."),
    /**
     *  CONCERT_SEAT_NOT_FOUND
     *  - API.6 예매
     */
    SEAT_NOT_AVAILABLE("The selected seat can not be booked."),
}