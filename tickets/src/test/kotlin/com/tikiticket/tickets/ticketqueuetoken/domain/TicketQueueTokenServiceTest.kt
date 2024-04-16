package com.tikiticket.tickets.ticketqueuetoken.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.time.LocalDateTime

class TicketQueueTokenServiceTest {

    // Mock Repository 생성
    private val ticketQueueTokenRepository: TicketQueueTokenRepository = mock(TicketQueueTokenRepository::class.java)

    // 테스트 대상 서비스
    private val ticketQueueTokenService = TicketQueueTokenService(ticketQueueTokenRepository)

    @Test
    fun `토큰을 발급한다`() {
        // Mock 객체가 호출될 때 반환할 예상 토큰 생성
        val newToken = TicketQueueToken(
            id = 0,
            userId = "user123",
            tokenStatus = TokenStatusType.WAITING,
            createdAt = null, // 원하는 시간으로 설정해야 함
            updatedAt = null // 원하는 시간으로 설정해야 함
        )

        val expectedToken = TicketQueueToken(
            id = 1,
            userId = "user123",
            tokenStatus = TokenStatusType.WAITING,
            createdAt = null, // 원하는 시간으로 설정해야 함
            updatedAt = null // 원하는 시간으로 설정해야 함
        )

        // Mock Repository의 createToken 메서드가 호출될 때 반환할 값 설정
        `when`(ticketQueueTokenRepository.createToken(newToken)).thenReturn(expectedToken)

        // createToken 메서드 호출
        val resultToken = ticketQueueTokenService.createToken("user123")

        // createToken 메서드의 반환값이 예상값과 일치하는지 확인
        assertEquals(expectedToken, resultToken)
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
        `when`(ticketQueueTokenRepository.retrieveToken(userId)).thenReturn(token)

        val foundToken = ticketQueueTokenService.retrieveToken(userId)

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
        `when`(ticketQueueTokenRepository.findTokenQueuePosition(token.tokenStatus, token.createdAt!!)).thenReturn(expectedPosition)

        val retrievedPosition = ticketQueueTokenService.retrieveQueuePosition(token.tokenStatus, token.createdAt!!)

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
        ticketQueueTokenService.modifyTokenStatus(token.tokenStatus, token.userId)

        // Then
        Mockito.verify(ticketQueueTokenRepository, Mockito.times(1)).modifyTokenStatus(token.tokenStatus, token.userId)
    }
}