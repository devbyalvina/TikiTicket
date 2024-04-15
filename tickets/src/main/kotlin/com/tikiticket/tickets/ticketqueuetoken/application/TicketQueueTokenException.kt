package com.tikiticket.tickets.ticketqueuetoken.application

class TicketQueueTokenException: RuntimeException {
    var error: TicketQueueTokenError
    var errorMessage: String

    constructor(ticketQueueTokenError: TicketQueueTokenError) : super(ticketQueueTokenError.message) {
        this.error = ticketQueueTokenError
        this.errorMessage = ticketQueueTokenError.message
    }

    constructor(ticketQueueTokenError: TicketQueueTokenError, message: String) : super(message) {
        this.error = ticketQueueTokenError
        this.errorMessage = message
    }
}