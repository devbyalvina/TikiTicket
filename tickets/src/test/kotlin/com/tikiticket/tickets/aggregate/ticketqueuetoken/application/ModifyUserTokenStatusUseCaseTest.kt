package com.tikiticket.tickets.aggregate.ticketqueuetoken.application

import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueToken
import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueTokenService
import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType
import com.tikiticket.tickets.global.domain.exception.CustomException
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ModifyUserTokenStatusUseCaseTest {

    private lateinit var tokenService: TicketQueueTokenService
    private lateinit var modifyUserTokenStatusUseCase: ModifyUserTokenStatusUseCase

    @BeforeEach
    fun setUp() {
        tokenService = mockk()
        modifyUserTokenStatusUseCase = ModifyUserTokenStatusUseCase(tokenService)
    }

    @Test
    fun `토큰 상태를 변경한다`() {
        // Given
        val userId = "user123"
        val now = LocalDateTime.now()
        val existingToken = TicketQueueToken(
            id = 1L,
            userId = userId,
            tokenStatus = TokenStatusType.WAITING,
            expiryDateTime = now.plusMinutes(5),
            createdAt = now,
            updatedAt = now
        )
        val command = ModifyUserTokenStatusCommand(userId, TokenStatusType.ACTIVE)
        val modifiedTokenStatus = TokenStatusType.ACTIVE

        every { tokenService.retrieveToken(userId) } returns existingToken
        every { tokenService.modifyTokenStatus(modifiedTokenStatus, userId) } just Runs

        // When
        val resultToken = modifyUserTokenStatusUseCase(command)

        // Then
        verify(exactly = 1) { tokenService.retrieveToken(userId) }
        verify(exactly = 1) { tokenService.modifyTokenStatus(modifiedTokenStatus, userId) }
        assertEquals(modifiedTokenStatus, resultToken.tokenStatus)
    }

    @Test
    fun `토큰이 없으면 예외를 반환한다`() {
        // Given
        val userId = "user123"
        val command = ModifyUserTokenStatusCommand(userId, TokenStatusType.ACTIVE)
        every { tokenService.retrieveToken(userId) } returns null

        // When, Then
        assertThrows(CustomException::class.java) {
            modifyUserTokenStatusUseCase(command)
        }
        verify(exactly = 0) { tokenService.modifyTokenStatus(TokenStatusType.ACTIVE, userId) }
    }
}