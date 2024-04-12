package com.tikiticket.tickets.ticketqueuetoken.domain

import org.springframework.stereotype.Service

@Service
class TicketQueueTokenService (
    private val ticketQueueRepository: TicketQueueRepository
) {
    /**
     * 토큰 생성
     */
    fun createToken(userId: String): TicketQueueToken {
        return ticketQueueRepository.createToken(userId)
    }

    /**
     * 토큰 조회
     */
    fun findTokenByUserId(userId: String): TicketQueueToken {
        return ticketQueueRepository.findTokenByUserId(userId)
    }

    /**
     * 토큰 순번 조회
     */
    fun retrieveQueuePosition(token: TicketQueueToken): Long {
        return ticketQueueRepository.retrieveQueuePosition(token)
    }

    /**
     * 토큰 상태 변경
     */
    fun modifyTokenStatus(token: TicketQueueToken) {
        ticketQueueRepository.updateTokenStatus(token)
    }
}