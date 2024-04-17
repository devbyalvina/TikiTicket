package com.tikiticket.tickets.ticketqueuetoken.infrastructure

import com.tikiticket.tickets.ticketqueuetoken.domain.TokenStatusType
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface TicketQueueTokenJpaRepository: JpaRepository<TicketQueueTokenEntity, Long> {
    /**
     * 유저 토큰 조회
     */
    fun findByUserId(userId: String): TicketQueueTokenEntity?

    /**
     * 유저 토큰 상태 변경
     */
    @Modifying
    @Query("update TicketQueueTokenEntity t set t.tokenStatus = :tokenStatus where t.userId = :userId")
    fun updateTokenStatusByUserId(tokenStatus: TokenStatusType, userId: String)

    /**
     *  토큰 순번 조회
     */
    @Query("select count(1) from TicketQueueTokenEntity t where t.tokenStatus = :tokenStatus and t.createdAt >= :tokenCreatedAt")
    fun countByTokenStatusAndCreatedAtGreaterThan(tokenStatus: TokenStatusType, tokenCreatedAt: LocalDateTime): Long

    /**
     * 상태 조건으로 토큰 갯수 조회
     */
    @Query("select count(1) from TicketQueueTokenEntity t where t.tokenStatus = :tokenStatus and t.expiryDateTime > :expiryDateTime")
    fun countByTokenStatusAndExpiryDateTimeGreaterThan(tokenStatus: TokenStatusType, expiryDateTime: LocalDateTime): Long

    /**
     * 상태 조건으로 토큰 N개 조회
     */
    @Query("select t FROM TicketQueueTokenEntity t WHERE t.status = :status order by t.createdAt")
    fun findTokensByStatusOrderedByCreationDate(tokenStatus: TokenStatusType, pageable: Pageable): List<TicketQueueTokenEntity>

    /**
     * 유효기간이 만료된 토큰 상태 변경
     */
    @Modifying
    @Query("update TicketQueueTokenEntity t set t.tokenStatus = :tokenStatus where t.expiryDateTime <= :expiryDateTime")
    fun updateTokenStatusByExpiryDateTimeLessThanEqual(tokenStatus: TokenStatusType, expiryDateTime: LocalDateTime)
}