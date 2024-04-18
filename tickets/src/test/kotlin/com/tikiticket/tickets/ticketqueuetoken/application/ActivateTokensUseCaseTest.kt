package com.tikiticket.tickets.ticketqueuetoken.application

import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueToken
import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueTokenService
import com.tikiticket.tickets.ticketqueuetoken.domain.TokenStatusType
import io.mockk.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ActivateTokensUseCaseTest {
    private val tokenService: TicketQueueTokenService = mockk()
    private val activateTokensUseCase = ActivateTokensUseCase(tokenService)

    @Test
    fun `토큰을 활성화 시킨다`() {
        // Given
        val maxTokenCount = 10
        val activeTokenCount = 5L
        val fetchTokenCount = maxTokenCount - activeTokenCount.toInt()
        every { tokenService.countTokensWithStatus(TokenStatusType.ACTIVE, any()) } returns activeTokenCount
        every { tokenService.changeTokenStatuses(TokenStatusType.WAITING, TokenStatusType.ACTIVE, fetchTokenCount) } just Runs

        // When
        activateTokensUseCase(maxTokenCount)

        // Then
        verify(exactly = 1) { tokenService.countTokensWithStatus(TokenStatusType.ACTIVE, any()) }
        verify(exactly = 1) { tokenService.changeTokenStatuses(TokenStatusType.WAITING, TokenStatusType.ACTIVE, fetchTokenCount) }
    }
}