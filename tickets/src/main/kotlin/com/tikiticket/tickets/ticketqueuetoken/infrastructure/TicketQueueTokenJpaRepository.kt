package com.tikiticket.tickets.ticketqueuetoken.infrastructure

import com.tikiticket.tickets.ticketqueuetoken.domain.TokenStatusType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface TicketQueueTokenJpaRepository: JpaRepository<TicketQueueTokenEntity, Long> {
    fun findByUserId(userId: String): TicketQueueTokenEntity?

    @Modifying
    @Query("update TicketQueueTokenEntity t set t.tokenStatus = :tokenStatus where t.userId = :userId")
    fun updateTicketQueueToken(tokenStatus: TokenStatusType, userId: String)

    @Query("select count(1) from TicketQueueTokenEntity t where t.tokenStatus = :tokenStatus and t.createdAt >= :tokenCreatedAt")
    fun countByTokenStatusAndCreatedAtGreaterThan(tokenStatus: TokenStatusType, tokenCreatedAt: LocalDateTime): Long
}