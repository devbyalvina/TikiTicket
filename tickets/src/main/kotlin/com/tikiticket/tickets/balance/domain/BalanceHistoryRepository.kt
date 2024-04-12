package com.tikiticket.tickets.balance.domain

interface BalanceHistoryRepository {
    /**
     *  잔고 히스토리 저장
     */
    fun save(balanceHistory: BalanceHistory): BalanceHistory
}