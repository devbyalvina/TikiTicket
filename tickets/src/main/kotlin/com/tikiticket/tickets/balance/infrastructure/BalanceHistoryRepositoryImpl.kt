package com.tikiticket.tickets.balance.infrastructure

import com.tikiticket.tickets.balance.domain.BalanceHistory
import com.tikiticket.tickets.balance.domain.BalanceHistoryRepository
import org.springframework.stereotype.Repository

@Repository
class BalanceHistoryRepositoryImpl(
    private val balanceHistoryJpaRepository: BalanceHistoryJpaRepository
): BalanceHistoryRepository {
    override fun save(balanceHistory: BalanceHistory): BalanceHistory {
        val balanceHistoryEntity = BalanceHistoryEntity.of(balanceHistory)
        return balanceHistoryJpaRepository.save(balanceHistoryEntity).toDomain()
    }
}