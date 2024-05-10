package com.tikiticket.tickets.aggregate.ticketqueuetoken.domain

import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class TicketQueueTokenServiceTest {

    // Mock Repository 생성
    private val ticketQueueTokenRepository: TicketQueueTokenRepository = mockk()

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
        every { ticketQueueTokenRepository.createToken(newToken) } returns expectedToken

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
        every { ticketQueueTokenRepository.retrieveToken(userId) } returns token

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
        every { ticketQueueTokenRepository.retrieveToken(token.userId) } returns token
        every { ticketQueueTokenRepository.findTokenQueuePosition(token.tokenStatus, token.createdAt!!) } returns expectedPosition

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

        every { ticketQueueTokenRepository.modifyTokenStatus(token.tokenStatus, token.userId) } just runs

        // When
        ticketQueueTokenService.modifyTokenStatus(token.tokenStatus, token.userId)

        // Then
        verify(exactly = 1) { ticketQueueTokenRepository.modifyTokenStatus(token.tokenStatus, token.userId) }
    }

    @Test
    fun `특정 상태를 갖고 있는 토큰의 갯수를 조회한다`() {
        // Given
        val tokenStatus = TokenStatusType.WAITING
        val expiryDateTime = LocalDateTime.now()

        val expectedTokenCount = 5L
        // Stubbing repository method
        every { ticketQueueTokenRepository.countTokensWithStatus(tokenStatus, expiryDateTime) } returns expectedTokenCount

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

        every { ticketQueueTokenRepository.changeTokenStatuses(previousStatus, targetStatus, tokenCount) } just runs

        // When
        ticketQueueTokenService.changeTokenStatuses(previousStatus, targetStatus, tokenCount)

        // Then
        // Verify that repository method is called with correct parameters
        verify(exactly = 1) { ticketQueueTokenRepository.changeTokenStatuses(previousStatus, targetStatus, tokenCount) }
    }

    @Test
    fun `스케줄러가 활성화 시킬 토큰의 갯수가 0개 보다 크면 해당 갯수만큼 토큰을 활성화시킨다`() {
        // Given
        val previousStatus = TokenStatusType.WAITING
        val targetStatus = TokenStatusType.ACTIVE
        val maxTokenCount = 5

        // When
        every { ticketQueueTokenRepository.countTokensWithStatus(targetStatus, any()) } returns 2
        every { ticketQueueTokenRepository.changeTokenStatuses(previousStatus, targetStatus, maxTokenCount - 2) } just runs
        ticketQueueTokenService.activateTokensByScheduler(previousStatus, targetStatus, maxTokenCount)

        // Then
        // Verify that repository method is called with correct parameters
        verify(exactly = 1) { ticketQueueTokenRepository.changeTokenStatuses(previousStatus, targetStatus, 3) }
    }

    @Test
    fun `스케줄러가 활성화 시킬 토큰의 갯수가 0개 이하이면 해당 갯수만큼 토큰을 활성화시킨다`() {
        // Given
        val previousStatus = TokenStatusType.WAITING
        val targetStatus = TokenStatusType.ACTIVE
        val maxTokenCount = 5

        // When
        every { ticketQueueTokenRepository.countTokensWithStatus(targetStatus, any()) } returns 5
        ticketQueueTokenService.activateTokensByScheduler(previousStatus, targetStatus, maxTokenCount)

        // Then
        // Verify that repository method is called with correct parameters
        verify(exactly = 0) { ticketQueueTokenRepository.changeTokenStatuses(previousStatus, targetStatus, 0) }
    }

    @Test
    fun `유효기간이 만료된 토큰의 상태를 변경시킨다`() {
        // Given
        val tokenStatus = TokenStatusType.WAITING
        val expiryDateTime = LocalDateTime.now()

        every { ticketQueueTokenRepository.modifyExpiredTokenStatus(tokenStatus, expiryDateTime) } just runs

        // When
        ticketQueueTokenService.modifyExpiredTokenStatus(tokenStatus, expiryDateTime)

        // Then
        // Verify that repository method is called with correct parameters
        verify(exactly = 1) { ticketQueueTokenRepository.modifyExpiredTokenStatus(tokenStatus, expiryDateTime) }
    }

    @Test
    fun `메모리 저장소에 토큰을 발급한다`() {
        // Given
        val userId = "user123"

        every { ticketQueueTokenRepository.createTokenInMemory(userId) } just runs
        every { ticketQueueTokenRepository.findWaitQueuePositionInMemory(userId) } returns 1

        // When
        val resultRank = ticketQueueTokenService.createWaitTokenInMemory("user123")

        // Then
        assertEquals(resultRank, 1)
    }

    @Test
    fun `메모리 저장소에 저장된 토큰의 대기열 순번을 확인한다`() {
        // Given
        val userId = "user123"

        every { ticketQueueTokenRepository.findWaitQueuePositionInMemory(userId) } returns 1

        // When
        val resultRank = ticketQueueTokenService.retrieveWaitQueuePositionInMemory("user123")

        // Then
        assertEquals(resultRank, 1)
    }
}