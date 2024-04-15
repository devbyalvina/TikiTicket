package com.tikiticket.tickets.ticketqueuetoken.application

import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueToken
import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueTokenService

/**
 *  API.3 대기열 토큰 조회
 */
class GetTokenUseCase (
    private val ticketQueueTokenService: TicketQueueTokenService
){
    operator fun invoke(userId: String): TicketQueueToken {
        return ticketQueueTokenService.retrieveToken(userId)
            ?: throw TicketQueueTokenException(TicketQueueTokenError.TOKEN_NOT_FOUND)
    }
}