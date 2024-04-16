package com.tikiticket.tickets.ticketqueuetoken.domain

import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TicketQueueTokenService (
    private val ticketQueueTokenRepository: TicketQueueTokenRepository
) {
    /**
     * 토큰 생성
     */
    fun createToken(userId: String): TicketQueueToken {
        val ticketQueueToken = TicketQueueToken (
            id = 0,
            userId = userId,
            tokenStatus = TokenStatusType.WAITING,
            null,
            null
        )
        return ticketQueueTokenRepository.createToken(ticketQueueToken)
    }

    /**
     * 토큰 조회
     */
    fun retrieveToken(userId: String): TicketQueueToken? {
        return ticketQueueTokenRepository.retrieveToken(userId)
    }

    /**
     * 토큰 상태 변경
     */
    fun modifyTokenStatus(tokenStatus: TokenStatusType, userId: String) {
        ticketQueueTokenRepository.modifyTokenStatus(tokenStatus, userId)
    }

    /**
     * 토큰 순번 조회
     */
    fun retrieveQueuePosition(tokenStatus: TokenStatusType, tokenCreatedAt: LocalDateTime): Long {
        return ticketQueueTokenRepository.findTokenQueuePosition(tokenStatus, tokenCreatedAt)
    }
}