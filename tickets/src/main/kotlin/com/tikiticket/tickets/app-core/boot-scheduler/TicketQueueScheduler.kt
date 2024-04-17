package com.tikiticket.tickets.`app-core`.`boot-scheduler`

import com.tikiticket.tickets.ticketqueuetoken.application.ActivateTokensUseCase
import com.tikiticket.tickets.ticketqueuetoken.application.InactivateTokensUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class TicketQueueScheduler (
    private val activateTokensUseCase: ActivateTokensUseCase,
    private val inactivateTokensUseCase: InactivateTokensUseCase
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