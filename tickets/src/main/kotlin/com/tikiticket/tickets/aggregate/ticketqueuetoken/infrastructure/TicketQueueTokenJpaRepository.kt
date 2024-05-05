package com.tikiticket.tickets.aggregate.ticketqueuetoken.infrastructure

import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface TicketQueueTokenJpaRepository: JpaRepository<com.tikiticket.tickets.aggregate.ticketqueuetoken.infrastructure.TicketQueueTokenEntity, Long> {
    /**
     * 유저 토큰 조회
     */
    fun findByUserId(userId: String): com.tikiticket.tickets.aggregate.ticketqueuetoken.infrastructure.TicketQueueTokenEntity?

    /**
     * 유저 토큰 상태 변경
     */
    @Modifying
    @Query("update TicketQueueTokenEntity t set t.tokenStatus = :tokenStatus where t.userId = :userId")
    fun updateTokenStatusByUserId(tokenStatus: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType, userId: String)

    /**
     *  토큰 순번 조회
     */
    @Query("select count(1) from TicketQueueTokenEntity t where t.tokenStatus = :tokenStatus and t.createdAt >= :tokenCreatedAt")
    fun countByTokenStatusAndCreatedAtGreaterThan(tokenStatus: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType, tokenCreatedAt: LocalDateTime): Long

    /**
     * 상태 조건으로 토큰 갯수 조회
     */
    @Query("select count(1) from TicketQueueTokenEntity t where t.tokenStatus = :tokenStatus and t.expiryDateTime > :expiryDateTime")
    fun countByTokenStatusAndExpiryDateTimeGreaterThan(tokenStatus: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType, expiryDateTime: LocalDateTime): Long

    /**
     * 상태 조건으로 토큰 N개 조회
     */
    @Query("select t FROM TicketQueueTokenEntity t WHERE t.tokenStatus = :tokenStatus order by t.createdAt")
    fun findTokensByStatusOrderedByCreationDate(tokenStatus: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType, pageable: Pageable): List<com.tikiticket.tickets.aggregate.ticketqueuetoken.infrastructure.TicketQueueTokenEntity>

    /**
     * 유효기간이 만료된 토큰 상태 변경
     */
    @Modifying
    @Query("update TicketQueueTokenEntity t set t.tokenStatus = :tokenStatus where t.expiryDateTime <= :expiryDateTime")
    fun updateTokenStatusByExpiryDateTimeLessThanEqual(tokenStatus: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType, expiryDateTime: LocalDateTime)
}