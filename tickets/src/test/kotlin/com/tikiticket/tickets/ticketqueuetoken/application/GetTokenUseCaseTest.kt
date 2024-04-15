package com.tikiticket.tickets.ticketqueuetoken.application

import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueToken
import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueTokenService
import com.tikiticket.tickets.ticketqueuetoken.domain.TokenStatusType
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class GetTokenUseCaseTest {
    private lateinit var tokenService: TicketQueueTokenService
    private lateinit var getTokenUseCase: GetTokenUseCase

    @BeforeEach
    fun setUp() {
        tokenService = mockk()
        getTokenUseCase = GetTokenUseCase(tokenService)
    }

    @Test
    fun `get token queue position - token found`() {
        // Given
        val userId = "user123"
        val existingToken = TicketQueueToken(
            id = 1L,
            userId = userId,
            tokenStatus = TokenStatusType.WAITING,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        every { tokenService.retrieveToken(userId) } returns existingToken

        // When
        val resultToken = getTokenUseCase(userId)

        // Then
        assertEquals(existingToken, resultToken)
        verify(exactly = 1) { tokenService.retrieveToken(userId) }
    }

    @Test
    fun `get token queue position - token not found`() {
        // Given
        val userId = "user123"
        every { tokenService.retrieveToken(userId) } returns null

        // When
        val exception = assertThrows(TicketQueueTokenException::class.java) {
            getTokenUseCase(userId)
        }

        // Then
        assertEquals(TicketQueueTokenError.TOKEN_NOT_FOUND, exception.error)
        verify(exactly = 1) { tokenService.retrieveToken(userId) }
    }

}