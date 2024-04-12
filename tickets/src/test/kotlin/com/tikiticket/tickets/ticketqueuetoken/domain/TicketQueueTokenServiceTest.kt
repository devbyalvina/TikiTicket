package com.tikiticket.tickets.ticketqueuetoken.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.time.LocalDateTime

class TicketQueueTokenServiceTest {

    // Mock Repository 생성
    private val ticketQueueRepository: TicketQueueRepository = mock(TicketQueueRepository::class.java)

    // 테스트 대상 서비스
    private val ticketQueueTokenService = TicketQueueTokenService(ticketQueueRepository)

    @Test
    fun `토큰을 발급한다`() {
        // Given
        val userId = "user123"
        val token = TicketQueueToken(
            1L,
            userId,
            TokenStatusType.WAITING,
            LocalDateTime.now(),
            LocalDateTime.now()
        )

        // When
        `when`(ticketQueueRepository.createToken(userId)).thenReturn(token)

        val createdToken = ticketQueueTokenService.createToken(userId)

        // Then
        assertEquals(token, createdToken)
    }

    @Test
    fun `UserId로 토큰을 조회한다`() {
        // Given
        val userId = "user123"
        val token = TicketQueueToken(
            1L,
            userId,
            TokenStatusType.WAITING,
            LocalDateTime.now(),
            LocalDateTime.now()
        )

        // When
        `when`(ticketQueueRepository.findTokenByUserId(userId)).thenReturn(token)

        val foundToken = ticketQueueTokenService.findTokenByUserId(userId)

        // Then
        assertEquals(token, foundToken)
    }

    @Test
    fun `토큰의 대기열 순번을 확인한다`() {
        // Given
        val token = TicketQueueToken(
            1L,
            "user123",
            TokenStatusType.WAITING,
            LocalDateTime.now(),
            LocalDateTime.now()
        )
        val expectedPosition = 5L

        // When
        `when`(ticketQueueRepository.retrieveQueuePosition(token)).thenReturn(expectedPosition)

        val retrievedPosition = ticketQueueTokenService.retrieveQueuePosition(token)

        // Then
        assertEquals(expectedPosition, retrievedPosition)
    }

    @Test
    fun `토큰의 상태를 변경한다`() {
        // Given
        val token = TicketQueueToken(
            1L,
            "user123",
            TokenStatusType.WAITING,
            LocalDateTime.now(),
            LocalDateTime.now()
        )

        // When
        ticketQueueTokenService.modifyTokenStatus(token)

        // Then
        // Then
        Mockito.verify(ticketQueueRepository, Mockito.times(1)).updateTokenStatus(token)
    }

}