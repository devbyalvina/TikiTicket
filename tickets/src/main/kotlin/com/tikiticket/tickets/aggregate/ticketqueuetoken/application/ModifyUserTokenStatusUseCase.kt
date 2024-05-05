package com.tikiticket.tickets.aggregate.ticketqueuetoken.application

import com.tikiticket.tickets.global.domain.exception.CustomException
import org.springframework.boot.logging.LogLevel
import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueToken
import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueTokenError
import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueTokenService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 *  API.2 대기열 토큰 상태 변경
 */
@Component
class ModifyUserTokenStatusUseCase (
    private val ticketQueueTokenService: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueTokenService
){
    @Transactional
    operator fun invoke(command: com.tikiticket.tickets.aggregate.ticketqueuetoken.application.ModifyUserTokenStatusCommand): com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueToken {
        val existingToken =  ticketQueueTokenService.retrieveToken(command.userId)
            ?: throw CustomException(LogLevel.INFO, com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueTokenError.TOKEN_NOT_FOUND)
        ticketQueueTokenService.modifyTokenStatus(command.tokenStatus, command.userId)
        return existingToken.copy(tokenStatus = command.tokenStatus)
    }
}