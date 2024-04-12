package com.tikiticket.tickets.balance.domain

import org.springframework.stereotype.Service

@Service
class BalanceService (
    private val balanceRepository: BalanceRepository
){
    /**
     *  잔고 조회
     */
    fun retrieveBalance(userId: String): Balance? {
        val balance = balanceRepository.findBalanceByUserId(userId)
        return balance
    }

    /**
     *  잔고 저장
     */
    fun storeBalance(balance: Balance): Balance {
        return balanceRepository.saveBalance(balance);
    }

    /**
     *  잔고 변경
     */
    fun modifyBalance(balance: Balance) {
        balanceRepository.updateBalance(balance)
    }
}