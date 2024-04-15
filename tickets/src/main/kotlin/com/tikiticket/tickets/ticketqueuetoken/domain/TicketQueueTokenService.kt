package com.tikiticket.tickets.ticketqueuetoken.domain

import org.springframework.stereotype.Service

@Service
class TicketQueueTokenService (
    private val ticketQueueTokenRepository: TicketQueueTokenRepository
) {
    /**
     * 토큰 생성
     */
    fun createToken(userId: String): TicketQueueToken {
        return ticketQueueTokenRepository.createToken(userId)
    }

    /**
     * 토큰 조회
     */
    fun retrieveToken(userId: String): TicketQueueToken? {
        return ticketQueueTokenRepository.retrieveToken(userId)
    }

    /**
     * 토큰 순번 조회
     */
    fun retrieveQueuePosition(token: TicketQueueToken): Long {
        return ticketQueueTokenRepository.retrieveTokenQueuePosition(token)
    }

    /**
     * 토큰 상태 변경
     */
    fun modifyTokenStatus(token: TicketQueueToken) {
        ticketQueueTokenRepository.modifyTokenStatus(token)
    }
}