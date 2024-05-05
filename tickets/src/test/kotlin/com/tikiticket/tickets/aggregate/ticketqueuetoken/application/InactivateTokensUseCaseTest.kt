package com.tikiticket.tickets.aggregate.ticketqueuetoken.application

import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueTokenService
import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
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