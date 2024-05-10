package com.tikiticket.tickets.global.application.scheduler

import com.tikiticket.tickets.aggregate.ticketqueuetoken.application.ActivateTokensBySchedulerUseCase
import com.tikiticket.tickets.aggregate.ticketqueuetoken.application.InactivateTokensUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class TicketQueueScheduler (
    private val activateTokensBySchedulerUseCase: ActivateTokensBySchedulerUseCase,
    private val inactivateTokensUseCase: InactivateTokensUseCase
) {
    @Scheduled(fixedRate = 3000)
    @Throws(InterruptedException::class)
    fun ActivateTokens() {
        activateTokensBySchedulerUseCase(10000)
    }

    @Scheduled(fixedRate = 3000)
    @Throws(InterruptedException::class)
    fun InactivateTokens() {
        inactivateTokensUseCase()
    }
}