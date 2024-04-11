package com.tikiticket.tickets.concert.domain.exception

class ConcertException: RuntimeException {
    var error: ConcertError
    var errorMessage: String

    constructor(concertError: ConcertError) : super(concertError.message) {
        this.error = concertError
        this.errorMessage = concertError.message
    }

    constructor(concertError: ConcertError, message: String) : super(message) {
        this.error = concertError
        this.errorMessage = message
    }
}