package com.tikiticket.tickets.aggregate.ticketqueuetoken.domain

import java.time.LocalDateTime

interface TicketQueueTokenRepository {
    /**
     * 유저 토큰 생성
     */
    fun createToken(ticketQueueToken: TicketQueueToken): TicketQueueToken

    /**
     * 유저 토큰 조회
     */
    fun retrieveToken(userId: String): TicketQueueToken?

    /**
     * 유저 토큰 상태 변경
     */
    fun modifyTokenStatus(tokenStatus: TokenStatusType, userId: String)

    /**
     * 유저 토큰 순번 조회
     */
    fun findTokenQueuePosition(tokenStatus: TokenStatusType, tokenCreatedAt: LocalDateTime): Long

    /**
     * 상태별 토큰 갯수 조회
     */
    fun countTokensWithStatus(tokenStatus: TokenStatusType, expiryDateTime: LocalDateTime): Long

    /**
     * N개 토큰 상태 변경
     */
    fun changeTokenStatuses(previousStatus: TokenStatusType, targetStatus: TokenStatusType, tokenCount: Int)

    /**
     * 유효기간이 만료된 토큰 상태 변경
     */
    fun modifyExpiredTokenStatus(tokenStatus: TokenStatusType, expiryDateTime: LocalDateTime)
}