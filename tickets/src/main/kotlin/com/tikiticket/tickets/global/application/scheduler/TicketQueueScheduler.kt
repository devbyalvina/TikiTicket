package com.tikiticket.tickets.global.application.scheduler

import com.tikiticket.tickets.aggregate.ticketqueuetoken.application.ActivateTokensUseCase
import com.tikiticket.tickets.aggregate.ticketqueuetoken.application.InactivateTokensUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class TicketQueueScheduler (
    private val activateTokensUseCase: com.tikiticket.tickets.aggregate.ticketqueuetoken.application.ActivateTokensUseCase,
    private val inactivateTokensUseCase: com.tikiticket.tickets.aggregate.ticketqueuetoken.application.InactivateTokensUseCase
) {
    @Scheduled(fixedRate = 3000)
    @Throws(InterruptedException::class)
    fun ActivateTokens() {
        activateTokensUseCase(10000)
    }

    @Scheduled(fixedRate = 3000)
    @Throws(InterruptedException::class)
    fun InactivateTokens() {
        inactivateTokensUseCase()
    }
}