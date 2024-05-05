package com.tikiticket.tickets.aggregate.ticketqueuetoken.application

import com.tikiticket.tickets.global.domain.exception.CustomException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class GetTokenUseCaseTest {
    private lateinit var tokenService: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueTokenService
    private lateinit var getTokenUseCase: com.tikiticket.tickets.aggregate.ticketqueuetoken.application.GetTokenUseCase

    @BeforeEach
    fun setUp() {
        tokenService = mockk()
        getTokenUseCase = com.tikiticket.tickets.aggregate.ticketqueuetoken.application.GetTokenUseCase(tokenService)
    }

    @Test
    fun `get token queue position - token found`() {
        // Given
        val userId = "user123"
        var currentTime = LocalDateTime.now()
        val existingToken = com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueToken(
            id = 1L,
            userId = userId,
            tokenStatus = com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType.WAITING,
            expiryDateTime = currentTime.plusMinutes(5),
            createdAt = currentTime,
            updatedAt = currentTime
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
        val exception = assertThrows(CustomException::class.java) {
            getTokenUseCase(userId)
        }

        // Then
        assertEquals(com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueTokenError.TOKEN_NOT_FOUND, exception.customError)
        verify(exactly = 1) { tokenService.retrieveToken(userId) }
    }

}