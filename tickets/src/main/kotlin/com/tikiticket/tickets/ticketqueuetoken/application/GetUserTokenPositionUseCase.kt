package com.tikiticket.tickets.ticketqueuetoken.application

import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueTokenService
import org.springframework.stereotype.Component

/**
 *  API.3 대기열 토큰 순번 조회
 */
@Component
class GetUserTokenPositionUseCase (
    private val ticketQueueTokenService: TicketQueueTokenService
){
    operator fun invoke(userId: String): Long {
        return ticketQueueTokenService.retrieveQueuePosition(userId)
    }
}