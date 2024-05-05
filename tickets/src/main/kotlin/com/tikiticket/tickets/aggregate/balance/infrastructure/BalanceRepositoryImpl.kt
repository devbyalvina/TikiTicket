package com.tikiticket.tickets.aggregate.balance.infrastructure

import com.tikiticket.tickets.aggregate.balance.domain.Balance
import com.tikiticket.tickets.aggregate.balance.domain.BalanceHistory
import com.tikiticket.tickets.aggregate.balance.domain.BalanceRepository
import org.springframework.stereotype.Repository

@Repository
class BalanceRepositoryImpl(
    private val balanceJpaRepository: BalanceJpaRepository,
    private val balanceHistoryJpaRepository: BalanceHistoryJpaRepository
): BalanceRepository {
    /**
     *  잔고 조회
     */
    override fun findBalance(userId: String): Balance? {
        TODO("Not yet implemented")
    }

    /**
     *  변경을 위한 잔고 조회
     */
    override fun findBalanceForUpdate(userId: String): Balance? {
        return balanceJpaRepository.findBalanceForUpdateById(userId)?.toDomain()
    }

    /**
     *  잔고 저장
     */
    override fun storeBalance(balance: Balance): Balance {
        val balanceEntity = BalanceEntity.of(balance)
        return balanceJpaRepository.save(balanceEntity).toDomain()
    }

    /**
     *  잔고 변경
     */
    override fun changeBalance(balance: Balance) {
        TODO("Not yet implemented")
    }

    /**
     *  잔고 히스토리 저장
     */
    override fun storeBalanceHistory(balanceHistory: BalanceHistory): BalanceHistory {
        val balanceHistoryEntity = BalanceHistoryEntity.of(balanceHistory)
        return balanceHistoryJpaRepository.save(balanceHistoryEntity).toDomain()
    }
}