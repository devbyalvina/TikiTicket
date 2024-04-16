package com.tikiticket.tickets.ticketqueuetoken.application

import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueToken
import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueTokenService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 *  API.2 대기열 토큰 상태 변경
 */
@Component
class ModifyTokenStatusUseCase (
    private val ticketQueueTokenService: TicketQueueTokenService
){
    @Transactional
    operator fun invoke(command: ModifyTokenStatusCommand): TicketQueueToken {
        val existingToken =  ticketQueueTokenService.retrieveToken(command.userId)
            ?: throw TicketQueueTokenException(TicketQueueTokenError.TOKEN_NOT_FOUND)
        val changedTokenStatus = command.getTokenStatusTypeFromString()
        ticketQueueTokenService.modifyTokenStatus(changedTokenStatus, command.userId)
        return existingToken.copy(tokenStatus = changedTokenStatus)
    }
}