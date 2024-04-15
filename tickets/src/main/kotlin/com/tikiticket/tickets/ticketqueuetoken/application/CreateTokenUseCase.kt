package com.tikiticket.tickets.ticketqueuetoken.application

import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueToken
import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueTokenService
import org.springframework.stereotype.Component

/**
 *  API.1 대기열 토큰 발급
 */
@Component
class CreateTokenUseCase (
    private val ticketQueueTokenService: TicketQueueTokenService
) {
    operator fun invoke(userId: String): TicketQueueToken {
        require (ticketQueueTokenService.retrieveToken(userId) == null) {
            throw TicketQueueTokenException(TicketQueueTokenError.TOKEN_ALREADY_ISSUED)
        }

        return ticketQueueTokenService.createToken(userId)
    }
}