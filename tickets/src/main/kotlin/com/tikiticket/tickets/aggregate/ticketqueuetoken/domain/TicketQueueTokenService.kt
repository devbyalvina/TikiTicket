package com.tikiticket.tickets.aggregate.ticketqueuetoken.domain

import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TicketQueueTokenService (
    private val ticketQueueTokenRepository: TicketQueueTokenRepository
) {
    /**
     * 유저 토큰 생성
     */
    fun createToken(userId: String, expiryDateTime: LocalDateTime): TicketQueueToken {
        val ticketQueueToken = TicketQueueToken(
            id = 0,
            userId = userId,
            tokenStatus = TokenStatusType.WAITING,
            expiryDateTime = expiryDateTime,
            null,
            null
        )
        return ticketQueueTokenRepository.createToken(ticketQueueToken)
    }

    /**
     * 유저 토큰 조회
     */
    fun retrieveToken(userId: String): TicketQueueToken? {
        return ticketQueueTokenRepository.retrieveToken(userId)
    }

    /**
     * 유저 토큰 상태 변경
     */
    fun modifyTokenStatus(tokenStatus: TokenStatusType, userId: String) {
        ticketQueueTokenRepository.modifyTokenStatus(tokenStatus, userId)
    }

    /**
     * 유저 토큰 순번 조회
     */
    fun retrieveQueuePosition(userId: String): Long {
        val userToken = ticketQueueTokenRepository.retrieveToken(userId)
        return ticketQueueTokenRepository.findTokenQueuePosition(userToken!!.tokenStatus, userToken.createdAt!!)
    }

    /**
     * 상태별 토큰 갯수 조회
     */
    fun countTokensWithStatus(tokenStatus: TokenStatusType, expiryDateTime: LocalDateTime): Long {
        return ticketQueueTokenRepository.countTokensWithStatus(tokenStatus, expiryDateTime)
    }

    /**
     * N개 토큰 상태 변경
     */
    fun changeTokenStatuses(previousStatus: TokenStatusType, targetStatus: TokenStatusType, tokenCount: Int) {
        ticketQueueTokenRepository.changeTokenStatuses(previousStatus, targetStatus,tokenCount)
    }

    /**
     * 토큰 활성화
     */
    fun activateTokensByScheduler(previousStatus: TokenStatusType, targetStatus: TokenStatusType, maxTokenCount: Int) {
        // ACTIVE 상태인 토큰 갯수 확인
        val activeTokenCount = ticketQueueTokenRepository.countTokensWithStatus(targetStatus, LocalDateTime.now())

        // 변경할 토큰 갯수
        val tokenFetchSize = maxTokenCount - activeTokenCount.toInt()

        // WAITING -> ACTIVE로 상태 변경
        when {
            tokenFetchSize > 0 -> ticketQueueTokenRepository.changeTokenStatuses(previousStatus, targetStatus, tokenFetchSize)
        }
    }

    /**
     * 유효기간이 만료된 토큰 상태 변경
     */
    fun modifyExpiredTokenStatus(tokenStatus: TokenStatusType, expiryDateTime: LocalDateTime) {
        ticketQueueTokenRepository.modifyExpiredTokenStatus(tokenStatus, expiryDateTime)
    }
}