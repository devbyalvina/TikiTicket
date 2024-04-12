package com.tikiticket.tickets.ticketqueuetoken.domain

interface TicketQueueRepository {
    /**
     * 토큰 생성
     */
    fun createToken(userId: String): TicketQueueToken

    /**
     * 토큰 조회
     */
    fun findTokenByUserId(userId: String): TicketQueueToken

    /**
     * 토큰 순번 조회
     */
    fun retrieveQueuePosition(token: TicketQueueToken): Long

    /**
     * 토큰 상태 변경
     */
    fun updateTokenStatus(token: TicketQueueToken)
}