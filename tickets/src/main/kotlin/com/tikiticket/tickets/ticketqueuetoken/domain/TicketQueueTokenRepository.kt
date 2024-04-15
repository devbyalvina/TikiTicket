package com.tikiticket.tickets.ticketqueuetoken.domain

interface TicketQueueTokenRepository {
    /**
     * 토큰 생성
     */
    fun createToken(userId: String): TicketQueueToken

    /**
     * 토큰 조회
     */
    fun retrieveToken(userId: String): TicketQueueToken

    /**
     * 토큰 순번 조회
     */
    fun retrieveTokenQueuePosition(token: TicketQueueToken): Long

    /**
     * 토큰 상태 변경
     */
    fun modifyTokenStatus(token: TicketQueueToken)
}