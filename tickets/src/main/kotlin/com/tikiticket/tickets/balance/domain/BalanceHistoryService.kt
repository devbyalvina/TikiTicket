package com.tikiticket.tickets.balance.domain

import org.springframework.stereotype.Service

@Service
class BalanceHistoryService (
    //private val balanceHistoryRepository: BalanceHistoryRepository
){
    /**
     *  잔고 이력 저장
     */
    fun storeBalanceHistory(balanceHistory: BalanceHistory) {
        //balanceHistoryRepository.save(balanceHistory);
    }
}