package com.tikiticket.tickets.ticketqueuetoken.domain

import java.time.LocalDateTime

interface TicketQueueTokenRepository {
    /**
     * 토큰 생성
     */
    fun createToken(ticketQueueToken: TicketQueueToken): TicketQueueToken

    /**
     * 토큰 조회
     */
    fun retrieveToken(userId: String): TicketQueueToken?

    /**
     * 토큰 상태 변경
     */
    fun modifyTokenStatus(tokenStatus: TokenStatusType, userId: String)

    /**
     * 토큰 순번 조회
     */
    fun findTokenQueuePosition(tokenStatus: TokenStatusType, tokenCreatedAt: LocalDateTime): Long
}