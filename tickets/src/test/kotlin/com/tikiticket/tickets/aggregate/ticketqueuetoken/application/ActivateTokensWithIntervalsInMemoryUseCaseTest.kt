package com.tikiticket.tickets.aggregate.ticketqueuetoken.application

import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueTokenService
import io.mockk.*
import org.junit.jupiter.api.Test

class ActivateTokensWithIntervalsInMemoryUseCaseTest {
    private val ticketQueueTokenService: TicketQueueTokenService = mockk()
    private val activateTokensWithIntervalsInMemoryUseCase = ActivateTokensWithIntervalsInMemoryUseCase(ticketQueueTokenService)

    @Test
    fun `메모리에 저장된 대기상태 토큰을 활성화 시킨다`() {
        // Given
        val maxTokenCount = 10
        every { ticketQueueTokenService.activateTokensWithIntervalsInMemory(maxTokenCount) } just Runs

        // When
        activateTokensWithIntervalsInMemoryUseCase(maxTokenCount)

        // Then
        verify(exactly = 1) { ticketQueueTokenService.activateTokensWithIntervalsInMemory(maxTokenCount) }
    }
}