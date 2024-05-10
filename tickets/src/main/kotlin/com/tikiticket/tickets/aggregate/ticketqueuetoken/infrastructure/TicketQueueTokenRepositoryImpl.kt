package com.tikiticket.tickets.aggregate.ticketqueuetoken.infrastructure

import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueToken
import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueTokenRepository
import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType
import jakarta.transaction.Transactional
import org.springframework.data.domain.Pageable
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class TicketQueueTokenRepositoryImpl (
    private val ticketQueueTokenJpaRepository: TicketQueueTokenJpaRepository,
    private val redisTemplate: RedisTemplate<String, Any>
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


    /**
     * 유저 토큰 생성 In Memory
     */
    override fun createTokenInMemory(userId: String) {
        redisTemplate.opsForZSet().add("WaitQueue", userId, System.currentTimeMillis().toDouble())
    }

    /**
     *  유저 토큰 순번 조회 In Memory
     */
    override fun findWaitQueuePositionInMemory(userId: String): Long? {
        return redisTemplate.opsForZSet().rank("WaitQueue", userId)
    }

    /**
     * 대기 상태 토큰을 활성 상태로 변경 In Memory
     */
    override fun activateTokensWithIntervalsInMemory(maxTokenCount: Int) {
        // ACTIVE 상태인 토큰 갯수 확인
        val activeTokenCount = redisTemplate.opsForZSet().size("ActiveQueue") ?: 0

        // 변경할 토큰 갯수
        val tokenFetchSize = maxTokenCount - activeTokenCount.toInt()

        // 변경할 토큰이 있는지 확인
        val tokensToActivate = tokenFetchSize.takeIf { it > 0 }

        // WAITING -> ACTIVE로 상태 변경
        tokensToActivate?.run {
            repeat(tokenFetchSize) {
                redisTemplate.opsForZSet().popMin("WaitQueue")?.let { waitUserId ->
                    redisTemplate.opsForZSet().add("ActiveQueue", waitUserId.toString(), System.currentTimeMillis().toDouble())
                }
            }
        }
    }
}