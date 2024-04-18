package com.tikiticket.tickets.ticketqueuetoken.application

import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueToken
import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueTokenService
import org.springframework.stereotype.Component

@Component
class GetTokenUseCase (
    private val ticketQueueTokenService: TicketQueueTokenService
){
    operator fun invoke(userId: String): TicketQueueToken {
        return ticketQueueTokenService.retrieveToken(userId)
            ?: throw TicketQueueTokenException(TicketQueueTokenError.TOKEN_NOT_FOUND)
    }
}