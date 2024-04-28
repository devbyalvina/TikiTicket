package com.tikiticket.tickets.balance.infrastructure

import com.tikiticket.tickets.balance.domain.Balance
import com.tikiticket.tickets.balance.domain.BalanceHistory
import com.tikiticket.tickets.balance.domain.BalanceRepository
import org.springframework.stereotype.Repository

@Repository
class BalanceRepositoryImpl: BalanceRepository {
    override fun findBalanceByUserId(userId: String): Balance? {
        TODO("Not yet implemented")
    }

    override fun findBalanceByUserIdForUpdate(userId: String): Balance? {
        TODO("Not yet implemented")
    }

    override fun saveBalance(balance: Balance): Balance {
        TODO("Not yet implemented")
    }

    override fun updateBalance(balance: Balance) {
        TODO("Not yet implemented")
    }

    override fun saveBalanceHistory(balanceHistory: BalanceHistory): BalanceHistory {
        TODO("Not yet implemented")
    }
}