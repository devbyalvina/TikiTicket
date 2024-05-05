package com.tikiticket.tickets.aggregate.ticketqueuetoken.application

import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueTokenService
import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * 스케줄러1. 대기 상태 토큰을 활성 상태로 변경
 */
@Component
class ActivateTokensUseCase(
    private val ticketQueueTokenService: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueTokenService
) {
    operator fun invoke(maxTokenCount: Int) {
        // ACTIVE 상태인 토큰 갯수 확인
        val activeTokenCount = ticketQueueTokenService.countTokensWithStatus(com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType.ACTIVE, LocalDateTime.now())

        // 변경할 토큰 갯수
        val tokenFetchSize = maxTokenCount - activeTokenCount.toInt()

        // WAITING -> ACTIVE로 상태 변경
        val watingTokens = ticketQueueTokenService.changeTokenStatuses(com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType.WAITING, com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType.ACTIVE, tokenFetchSize)
    }
}