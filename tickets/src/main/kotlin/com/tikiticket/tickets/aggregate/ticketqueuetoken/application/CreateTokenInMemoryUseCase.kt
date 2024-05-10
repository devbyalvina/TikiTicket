package com.tikiticket.tickets.aggregate.ticketqueuetoken.application

import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueTokenError
import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueTokenService
import com.tikiticket.tickets.global.domain.exception.CustomException
import org.springframework.boot.logging.LogLevel
import org.springframework.stereotype.Component

/**
 *  API.1 대기열 토큰 발급 - In Memory
 */
@Component
class CreateTokenInMemoryUseCase (
    private val ticketQueueTokenService: TicketQueueTokenService
) {
    operator fun invoke(userId: String): Long {
        require (ticketQueueTokenService.retrieveWaitQueuePositionInMemory(userId) == null) {
            throw CustomException(LogLevel.INFO, TicketQueueTokenError.TOKEN_ALREADY_ISSUED)
        }

        return ticketQueueTokenService.createWaitTokenInMemory(userId)
    }
}