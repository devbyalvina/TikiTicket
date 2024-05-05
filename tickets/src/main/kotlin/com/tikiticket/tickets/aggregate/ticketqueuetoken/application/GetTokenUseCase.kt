package com.tikiticket.tickets.aggregate.ticketqueuetoken.application

import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueToken
import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueTokenError
import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueTokenService
import com.tikiticket.tickets.global.domain.exception.CustomException
import org.springframework.boot.logging.LogLevel
import org.springframework.stereotype.Component

@Component
class GetTokenUseCase (
    private val ticketQueueTokenService: TicketQueueTokenService
){
    operator fun invoke(userId: String): TicketQueueToken {
        return ticketQueueTokenService.retrieveToken(userId)
            ?: throw CustomException(LogLevel.INFO, TicketQueueTokenError.TOKEN_NOT_FOUND)
    }
}