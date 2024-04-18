package com.tikiticket.tickets.ticketqueuetoken.application

import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueTokenService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GetUserTokenPositionUseCaseTest {
    private val tokenService: TicketQueueTokenService = mockk()
    private val getUserTokenPositionUseCase = GetUserTokenPositionUseCase(tokenService)

    @Test
    fun `유저 토큰 순번을 확인한다`() {
        // Given
        val userId = "user123"
        val expectedPosition = 5L
        every { tokenService.retrieveQueuePosition(userId) } returns expectedPosition

        // When
        val position = getUserTokenPositionUseCase(userId)

        // Then
        assertEquals(expectedPosition, position)
    }
}