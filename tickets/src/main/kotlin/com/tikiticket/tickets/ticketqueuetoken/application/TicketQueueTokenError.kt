package com.tikiticket.tickets.ticketqueuetoken.application

enum class TicketQueueTokenError (
    val message: String,
) {
    /**
     *  TOKEN_ALREADY_ISSUED
     *  - API.1 대기열 토큰 발급
     */
    TOKEN_ALREADY_ISSUED("Token already issued. Please use the existing token."),
    /**
     *  TOKEN_NOT_FOUND
     *  - API.2 대기열 토큰 상태 변경
     */
    TOKEN_NOT_FOUND("The token of the user does not exist in the queue"),
    /**
     *  WRONG_TOKEN_STATUS
     *  - API.2 대기열 토큰 상태 변경
     */
    WRONG_TOKEN_STATUS("Requested token status cannot be used.")
}