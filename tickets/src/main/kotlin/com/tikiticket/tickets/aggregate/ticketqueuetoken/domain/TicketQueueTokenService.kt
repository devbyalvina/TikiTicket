package com.tikiticket.tickets.aggregate.ticketqueuetoken.domain

import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TicketQueueTokenService (
    private val ticketQueueTokenRepository: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueTokenRepository
) {
    /**
     * 유저 토큰 생성
     */
    fun createToken(userId: String, expiryDateTime: LocalDateTime): com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueToken {
        val ticketQueueToken = com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueToken(
            id = 0,
            userId = userId,
            tokenStatus = com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType.WAITING,
            expiryDateTime = expiryDateTime,
            null,
            null
        )
        return ticketQueueTokenRepository.createToken(ticketQueueToken)
    }

    /**
     * 유저 토큰 조회
     */
    fun retrieveToken(userId: String): com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueToken? {
        return ticketQueueTokenRepository.retrieveToken(userId)
    }

    /**
     * 유저 토큰 상태 변경
     */
    fun modifyTokenStatus(tokenStatus: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType, userId: String) {
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
    fun countTokensWithStatus(tokenStatus: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType, expiryDateTime: LocalDateTime): Long {
        return ticketQueueTokenRepository.countTokensWithStatus(tokenStatus, expiryDateTime)
    }

    /**
     * N개 토큰 상태 변경
     */
    fun changeTokenStatuses(previousStatus: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType, targetStatus: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType, tokenCount: Int) {
        ticketQueueTokenRepository.changeTokenStatuses(previousStatus, targetStatus,tokenCount)
    }

    /**
     * 유효기간이 만료된 토큰 상태 변경
     */
    fun modifyExpiredTokenStatus(tokenStatus: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType, expiryDateTime: LocalDateTime) {
        ticketQueueTokenRepository.modifyExpiredTokenStatus(tokenStatus, expiryDateTime)
    }
}