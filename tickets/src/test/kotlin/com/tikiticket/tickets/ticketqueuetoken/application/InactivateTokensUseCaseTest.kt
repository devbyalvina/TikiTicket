package com.tikiticket.tickets.ticketqueuetoken.application

import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueTokenService
import com.tikiticket.tickets.ticketqueuetoken.domain.TokenStatusType
import io.mockk.*
import org.junit.jupiter.api.Test

class InactivateTokensUseCaseTest {
    private val tokenService: TicketQueueTokenService = mockk()
    private val inactivateTokensUseCase = InactivateTokensUseCase(tokenService)

    @Test
    fun `토큰을 비활성화 시킨다`() {
        // Given
        every { tokenService.modifyExpiredTokenStatus(TokenStatusType.INACTIVE, any()) } just Runs

        // When
        inactivateTokensUseCase()

        // Then
        verify(exactly = 1) { tokenService.modifyExpiredTokenStatus(TokenStatusType.INACTIVE, any()) }
    }
}