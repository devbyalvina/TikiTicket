package com.tikiticket.tickets.booking.application.exception

class BookingException: RuntimeException {
    var error: BookingError
    var errorMessage: String

    constructor(bookingError: BookingError) : super(bookingError.message) {
        this.error = bookingError
        this.errorMessage = bookingError.message
    }

    constructor(bookingError: BookingError, message: String) : super(message) {
        this.error = bookingError
        this.errorMessage = message
    }
}