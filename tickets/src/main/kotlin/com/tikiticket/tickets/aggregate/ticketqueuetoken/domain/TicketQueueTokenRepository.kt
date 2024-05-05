package com.tikiticket.tickets.aggregate.ticketqueuetoken.domain

import java.time.LocalDateTime

interface TicketQueueTokenRepository {
    /**
     * 유저 토큰 생성
     */
    fun createToken(ticketQueueToken: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueToken): com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueToken

    /**
     * 유저 토큰 조회
     */
    fun retrieveToken(userId: String): com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueToken?

    /**
     * 유저 토큰 상태 변경
     */
    fun modifyTokenStatus(tokenStatus: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType, userId: String)

    /**
     * 유저 토큰 순번 조회
     */
    fun findTokenQueuePosition(tokenStatus: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType, tokenCreatedAt: LocalDateTime): Long

    /**
     * 상태별 토큰 갯수 조회
     */
    fun countTokensWithStatus(tokenStatus: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType, expiryDateTime: LocalDateTime): Long

    /**
     * N개 토큰 상태 변경
     */
    fun changeTokenStatuses(previousStatus: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType, targetStatus: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType, tokenCount: Int)

    /**
     * 유효기간이 만료된 토큰 상태 변경
     */
    fun modifyExpiredTokenStatus(tokenStatus: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType, expiryDateTime: LocalDateTime)
}