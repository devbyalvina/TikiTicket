package com.tikiticket.tickets.aggregate.ticketqueuetoken.application

import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueTokenError
import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueTokenService
import com.tikiticket.tickets.global.domain.exception.CustomException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CreateTokenInMemoryUseCaseTest {
    private lateinit var ticketQueueTokenService: TicketQueueTokenService
    private lateinit var createTokenInMemoryUseCase: CreateTokenInMemoryUseCase

    @BeforeEach
    fun setUp() {
        ticketQueueTokenService = mockk()
        createTokenInMemoryUseCase = CreateTokenInMemoryUseCase(ticketQueueTokenService)
    }

    @Test
    fun `메모리 저장소에 토큰을 생성한다`() {
        // Given
        val userId = "user123"

        every { ticketQueueTokenService.retrieveWaitQueuePositionInMemory(userId) } returns null
        every { ticketQueueTokenService.createWaitTokenInMemory(userId) } returns 1

        // When
        val result = createTokenInMemoryUseCase(userId)

        // Then
        assertEquals(1, result)
    }

    @Test
    fun `메모리 저장소에 이미 토큰을 발급받은 유저이면 에러를 발생시킨다`() {
        // Given
        val userId = "user123"

        every { ticketQueueTokenService.retrieveWaitQueuePositionInMemory(userId) } returns 1

        // When & Then
        val exception = assertThrows(CustomException::class.java) {
            createTokenInMemoryUseCase(userId)
        }
        assertEquals(TicketQueueTokenError.TOKEN_ALREADY_ISSUED, exception.customError)
    }
}