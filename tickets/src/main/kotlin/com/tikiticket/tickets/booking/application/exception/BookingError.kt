package com.tikiticket.tickets.booking.application.exception

import com.tikiticket.tickets.appcore.application.exception.CustomError

enum class BookingError (
    override val message: String,
): CustomError {
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
     *  SEAT_NOT_AVAILABLE
     *  - API.6 예매
     */
    SEAT_NOT_AVAILABLE("The selected seat can not be booked."),
    /**
     *  BOOKING_NOT_FOUND
     *  - API.7 예매 내역 조회
     */
    BOOKING_NOT_FOUND("The specified booking was not found.");

    override val errorCode: String = name
}