package com.tikiticket.tickets.ticketqueuetoken.application

import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueToken
import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueTokenService
import com.tikiticket.tickets.ticketqueuetoken.domain.TokenStatusType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class CreateTokenUseCaseTest {
    private lateinit var ticketQueueTokenService: TicketQueueTokenService
    private lateinit var createTokenUseCase: CreateTokenUseCase

    @BeforeEach
    fun setUp() {
        ticketQueueTokenService = mockk()
        createTokenUseCase = CreateTokenUseCase(ticketQueueTokenService)
    }

    @Test
    fun `토큰을 생성한다`() {
        // Given
        val userId = "user123"
        val currentTime = LocalDateTime.now()
        val token = TicketQueueToken(1L, userId, TokenStatusType.ACTIVE, currentTime, currentTime)

        every { ticketQueueTokenService.retrieveToken(userId) } returns null
        every { ticketQueueTokenService.createToken(userId) } returns token

        // When
        val result = createTokenUseCase(userId)

        // Then
        assertEquals(token, result)
    }

    @Test
    fun `이미 토큰을 발급받은 유저이면 에러를 발생시킨다`() {
        // Given
        val userId = "user123"
        val existingToken = TicketQueueToken(1L, userId, TokenStatusType.ACTIVE, LocalDateTime.now(), LocalDateTime.now())

        every { ticketQueueTokenService.retrieveToken(userId) } returns existingToken

        // When & Then
        assertThrows(TicketQueueTokenException::class.java) {
            createTokenUseCase(userId)
        }
    }
}