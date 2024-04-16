package com.tikiticket.tickets.ticketqueuetoken.infrastructure

import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueToken
import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueTokenRepository
import com.tikiticket.tickets.ticketqueuetoken.domain.TokenStatusType
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class TicketQueueTokenRepositoryImpl (
    private val ticketQueueTokenJpaRepository: TicketQueueTokenJpaRepository
): TicketQueueTokenRepository {
    /**
     *  토큰 생성
     */
    override fun createToken(ticketQueueToken: TicketQueueToken): TicketQueueToken {
        val ticketQueueTokenEntity = TicketQueueTokenEntity.of(ticketQueueToken)
        return ticketQueueTokenJpaRepository.save(ticketQueueTokenEntity).toDomain()
    }

    /**
     *  토큰 조회
     */
    override fun retrieveToken(userId: String): TicketQueueToken? {
        return ticketQueueTokenJpaRepository.findByUserId(userId)?.toDomain()
    }

    /**
     *  토큰 상태 변경
     */
    override fun modifyTokenStatus(tokenStatus: TokenStatusType, userId: String) {
        ticketQueueTokenJpaRepository.updateTicketQueueToken(tokenStatus, userId)
    }

    /**
     *  토큰 순번 조회
     */
    override fun findTokenQueuePosition(tokenStatus: TokenStatusType, tokenCreatedAt: LocalDateTime): Long {
        return ticketQueueTokenJpaRepository.countByTokenStatusAndCreatedAtGreaterThan(tokenStatus, tokenCreatedAt)
    }
}