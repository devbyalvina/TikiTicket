package com.tikiticket.tickets.ticketqueuetoken.domain

import io.mockk.every
import io.mockk.verify
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
        val expiryDateTime = LocalDateTime.now().plusMinutes(5)

        // Mock 객체가 호출될 때 반환할 예상 토큰 생성
        val newToken = TicketQueueToken(
            id = 0,
            userId = "user123",
            tokenStatus = TokenStatusType.WAITING,
            expiryDateTime = expiryDateTime,
            createdAt = null,
            updatedAt = null
        )

        val expectedToken = TicketQueueToken(
            id = 1,
            userId = "user123",
            tokenStatus = TokenStatusType.WAITING,
            expiryDateTime = expiryDateTime,
            createdAt = null,
            updatedAt = null
        )

        // Mock Repository의 createToken 메서드가 호출될 때 반환할 값 설정
        `when`(ticketQueueTokenRepository.createToken(newToken)).thenReturn(expectedToken)

        // createToken 메서드 호출
        val resultToken = ticketQueueTokenService.createToken("user123", expiryDateTime)

        // createToken 메서드의 반환값이 예상값과 일치하는지 확인
        assertEquals(expectedToken, resultToken)
    }

    @Test
    fun `UserId로 토큰을 조회한다`() {
        // Given
        val userId = "user123"
        val expiryDateTime = LocalDateTime.now().plusMinutes(5)

        val token = TicketQueueToken(
            1L,
            userId,
            TokenStatusType.WAITING,
            expiryDateTime,
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
        val expiryDateTime = LocalDateTime.now().plusMinutes(5)

        val token = TicketQueueToken(
            1L,
            "user123",
            TokenStatusType.WAITING,
            expiryDateTime,
            LocalDateTime.now(),
            LocalDateTime.now()
        )
        val expectedPosition = 5L

        // When
        `when`(ticketQueueTokenRepository.retrieveToken(token.userId)).thenReturn(token)
        `when`(ticketQueueTokenRepository.findTokenQueuePosition(token.tokenStatus, token.createdAt!!)).thenReturn(expectedPosition)

        val retrievedPosition = ticketQueueTokenService.retrieveQueuePosition("user123")

        // Then
        assertEquals(expectedPosition, retrievedPosition)
    }

    @Test
    fun `토큰의 상태를 변경한다`() {
        // Given
        val expiryDateTime = LocalDateTime.now().plusMinutes(5)

        val token = TicketQueueToken(
            1L,
            "user123",
            TokenStatusType.WAITING,
            expiryDateTime,
            LocalDateTime.now(),
            LocalDateTime.now()
        )

        // When
        ticketQueueTokenService.modifyTokenStatus(token.tokenStatus, token.userId)

        // Then
        Mockito.verify(ticketQueueTokenRepository, Mockito.times(1)).modifyTokenStatus(token.tokenStatus, token.userId)
    }

    @Test
    fun `특정 상태를 갖고 있는 토큰의 갯수를 조회한다`() {
        // Given
        val tokenStatus = TokenStatusType.WAITING
        val expiryDateTime = LocalDateTime.now()

        val expectedTokenCount = 5L
        // Stubbing repository method
        `when`(ticketQueueTokenRepository.countTokensWithStatus(tokenStatus, expiryDateTime)).thenReturn(expectedTokenCount)

        // When
        val result = ticketQueueTokenService.countTokensWithStatus(tokenStatus, expiryDateTime)

        // Then
        assertEquals(expectedTokenCount, result)
    }

    @Test
    fun `특정 상태를 타겟 상태로 입력받은 갯수만큼 변경시킨다`() {
        // Given
        val previousStatus = TokenStatusType.WAITING
        val targetStatus = TokenStatusType.ACTIVE
        val tokenCount = 3

        // When
        ticketQueueTokenService.changeTokenStatuses(previousStatus, targetStatus, tokenCount)

        // Then
        // Verify that repository method is called with correct parameters
        Mockito.verify(ticketQueueTokenRepository, Mockito.times(1)).changeTokenStatuses(previousStatus, targetStatus, tokenCount)
    }

    @Test
    fun `유효기간이 만료된 토큰의 상태를 변경시킨다`() {
        // Given
        val tokenStatus = TokenStatusType.WAITING
        val expiryDateTime = LocalDateTime.now()

        // When
        ticketQueueTokenService.modifyExpiredTokenStatus(tokenStatus, expiryDateTime)

        // Then
        // Verify that repository method is called with correct parameters
        Mockito.verify(ticketQueueTokenRepository, Mockito.times(1)).modifyExpiredTokenStatus(tokenStatus, expiryDateTime)
    }
}