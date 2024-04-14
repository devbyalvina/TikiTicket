package com.tikiticket.tickets.balance.domain

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class BalanceService (
    private val balanceRepository: BalanceRepository,
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

    /**
     *  잔고 이력 저장
     */
    fun storeBalanceHistory(balanceHistory: BalanceHistory) {
        balanceRepository.saveBalanceHistory(balanceHistory);
    }

    /**
     *  잔고 변경
     */
    @Transactional
    fun changeBalance(existingBalance: Balance, transactionType: TransactionType, calculatedAmount: Long, currentDateTime: LocalDateTime): Balance {
        // 잔고 히스토리 저장
        val changedBalanceHistory = BalanceHistory (
            userId = existingBalance.userId,
            balanceHistoryId = 0,
            balanceAmount = calculatedAmount,
            createdAt = currentDateTime,
        )
        storeBalanceHistory(changedBalanceHistory)

        // 잔고 변경 내역 저장
        val changedBalance = existingBalance.copy(balanceAmount = calculatedAmount, updatedAt = currentDateTime)
        modifyBalance(changedBalance)
        return changedBalance
    }
}