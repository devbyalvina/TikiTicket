package com.tikiticket.tickets.balance.infrastructure

import com.tikiticket.tickets.balance.domain.Balance
import com.tikiticket.tickets.balance.domain.BalanceHistory
import com.tikiticket.tickets.balance.domain.BalanceRepository
import org.springframework.stereotype.Repository

@Repository
class BalanceRepositoryImpl: BalanceRepository {
    override fun findBalance(userId: String): Balance? {
        TODO("Not yet implemented")
    }

    override fun findBalanceForUpdate(userId: String): Balance? {
        TODO("Not yet implemented")
    }

    override fun storeBalance(balance: Balance): Balance {
        TODO("Not yet implemented")
    }

    override fun changeBalance(balance: Balance) {
        TODO("Not yet implemented")
    }

    override fun storeBalanceHistory(balanceHistory: BalanceHistory): BalanceHistory {
        TODO("Not yet implemented")
    }

}