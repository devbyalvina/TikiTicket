package com.tikiticket.tickets.ticketqueuetoken.application

enum class TicketQueueTokenError (
    val message: String,
) {
    /**
     *  TOKEN_ALREADY_ISSUED
     *  - API.1 대기열 토큰 발급
     */
    TOKEN_ALREADY_ISSUED("Token already issued. Please use the existing token.")
}