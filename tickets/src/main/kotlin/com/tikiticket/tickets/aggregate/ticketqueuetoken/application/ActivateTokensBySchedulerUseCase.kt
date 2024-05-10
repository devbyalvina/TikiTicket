package com.tikiticket.tickets.aggregate.ticketqueuetoken.application

import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueTokenService
import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType
import org.springframework.stereotype.Component

/**
 * 스케줄러1. 대기 상태 토큰을 활성 상태로 변경
 */
@Component
class ActivateTokensBySchedulerUseCase(
    private val ticketQueueTokenService: TicketQueueTokenService
) {
    operator fun invoke(maxTokenCount: Int) {
        ticketQueueTokenService.activateTokensByScheduler(TokenStatusType.WAITING, TokenStatusType.ACTIVE, maxTokenCount)
    }
}