package com.tikiticket.tickets.ticketqueuetoken.application

import com.tikiticket.tickets.appcore.application.exception.CustomException
import com.tikiticket.tickets.appcore.application.log.LogLevel
import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueToken
import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueTokenError
import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueTokenService
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