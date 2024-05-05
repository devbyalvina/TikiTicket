package com.tikiticket.tickets.aggregate.ticketqueuetoken.application

import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueTokenService
import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * 스케줄러2. 활성 상태 토큰을 비활성 상태로 변경
 */
@Component
class InactivateTokensUseCase(
    private val ticketQueueTokenService: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueTokenService
) {
    operator fun invoke() {
        ticketQueueTokenService.modifyExpiredTokenStatus(com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType.INACTIVE, LocalDateTime.now())
    }
}