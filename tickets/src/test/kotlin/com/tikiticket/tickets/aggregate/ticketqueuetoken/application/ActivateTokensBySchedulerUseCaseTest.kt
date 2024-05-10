package com.tikiticket.tickets.aggregate.ticketqueuetoken.application

import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueTokenService
import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class ActivateTokensBySchedulerUseCaseTest {
    private val ticketQueueTokenService: TicketQueueTokenService = mockk()
    private val activateBySchedulerTokensUseCase = ActivateTokensBySchedulerUseCase(ticketQueueTokenService)

    @Test
    fun `토큰을 활성화 시킨다`() {
        // Given
        val maxTokenCount = 10
        every { ticketQueueTokenService.activateTokensByScheduler(TokenStatusType.WAITING, TokenStatusType.ACTIVE, maxTokenCount) } just Runs

        // When
        activateBySchedulerTokensUseCase(maxTokenCount)

        // Then
        verify(exactly = 1) { ticketQueueTokenService.activateTokensByScheduler(TokenStatusType.WAITING, TokenStatusType.ACTIVE, maxTokenCount) }
    }
}