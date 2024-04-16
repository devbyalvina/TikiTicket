package com.tikiticket.tickets.ticketqueuetoken.application

import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueToken
import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueTokenService
import com.tikiticket.tickets.ticketqueuetoken.domain.TokenStatusType
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

class ModifyTokenStatusUseCaseTest {

    private lateinit var tokenService: TicketQueueTokenService
    private lateinit var modifyTokenStatusUseCase: ModifyTokenStatusUseCase

    @BeforeEach
    fun setUp() {
        tokenService = mockk()
        modifyTokenStatusUseCase = ModifyTokenStatusUseCase(tokenService)
    }

    @Test
    fun `토큰 상태를 변경한다`() {
        // Given
        val userId = "user123"
        val existingToken = TicketQueueToken(
            id = 1L,
            userId = userId,
            tokenStatus = TokenStatusType.WAITING,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        val command = ModifyTokenStatusCommand(userId, "ACTIVE")
        val modifiedTokenStatus = TokenStatusType.ACTIVE

        every { tokenService.retrieveToken(userId) } returns existingToken
        every { tokenService.modifyTokenStatus(any()) } just Runs

        // When
        val resultToken = modifyTokenStatusUseCase(command)

        // Then
        verify(exactly = 1) { tokenService.retrieveToken(userId) }
        verify(exactly = 1) { tokenService.modifyTokenStatus(any()) }
        assertEquals(modifiedTokenStatus, resultToken.tokenStatus)
    }

    @Test
    fun `토큰이 없으면 예외를 반환한다`() {
        // Given
        val userId = "user123"
        val command = ModifyTokenStatusCommand(userId, "ACTIVE")
        every { tokenService.retrieveToken(userId) } returns null

        // When, Then
        assertThrows(TicketQueueTokenException::class.java) {
            modifyTokenStatusUseCase(command)
        }
        verify(exactly = 0) { tokenService.modifyTokenStatus(any()) }
    }
}