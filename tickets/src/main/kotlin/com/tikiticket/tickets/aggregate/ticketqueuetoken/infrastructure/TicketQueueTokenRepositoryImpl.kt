package com.tikiticket.tickets.aggregate.ticketqueuetoken.infrastructure

import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueToken
import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueTokenRepository
import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType
import jakarta.transaction.Transactional
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class TicketQueueTokenRepositoryImpl (
    private val ticketQueueTokenJpaRepository: TicketQueueTokenJpaRepository
): TicketQueueTokenRepository {
    /**
     *  유저 토큰 생성
     */
    override fun createToken(ticketQueueToken: TicketQueueToken): TicketQueueToken {
        val ticketQueueTokenEntity =
            TicketQueueTokenEntity.of(
                ticketQueueToken
            )
        return ticketQueueTokenJpaRepository.save(ticketQueueTokenEntity).toDomain()
    }

    /**
     *  유저 토큰 조회
     */
    override fun retrieveToken(userId: String): TicketQueueToken? {
        return ticketQueueTokenJpaRepository.findByUserId(userId)?.toDomain()
    }

    /**
     *  유저 토큰 상태 변경
     */
    override fun modifyTokenStatus(tokenStatus: TokenStatusType, userId: String) {
        ticketQueueTokenJpaRepository.updateTokenStatusByUserId(tokenStatus, userId)
    }

    /**
     *  유저 토큰 순번 조회
     */
    override fun findTokenQueuePosition(tokenStatus: TokenStatusType, tokenCreatedAt: LocalDateTime): Long {
        return ticketQueueTokenJpaRepository.countByTokenStatusAndCreatedAtGreaterThan(tokenStatus, tokenCreatedAt)
    }

    /**
     * 상태별 토큰 갯수 조회
     */
    override fun countTokensWithStatus(tokenStatus: TokenStatusType, expiryDateTime: LocalDateTime): Long {
        return ticketQueueTokenJpaRepository.countByTokenStatusAndExpiryDateTimeGreaterThan(tokenStatus, expiryDateTime)
    }

    /**
     * N개 토큰 상태 변경
     */
    @Transactional
    override fun changeTokenStatuses(previousStatus: TokenStatusType, targetStatus: TokenStatusType, tokenCount: Int) {
        val targetTokenEntities = ticketQueueTokenJpaRepository.findTokensByStatusOrderedByCreationDate(previousStatus, Pageable.ofSize(tokenCount))

        targetTokenEntities.forEach { tokenEntity ->
            tokenEntity.tokenStatus = targetStatus
        }
    }

    /**
     * 유효기간이 만료된 토큰 상태 변경
     */
    override fun modifyExpiredTokenStatus(tokenStatus: TokenStatusType, expiryDateTime: LocalDateTime) {
        return ticketQueueTokenJpaRepository.updateTokenStatusByExpiryDateTimeLessThanEqual(tokenStatus, expiryDateTime)
    }
}