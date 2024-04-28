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
     *  잔고 조회 For Update
     */
    fun retrieveBalanceForUpdate(userId: String): Balance? {
        val balance = balanceRepository.findBalanceByUserIdForUpdate(userId)
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
    @Transactional
    fun changeBalance(existingBalance: Balance, transactionType: TransactionType, calculatedAmount: Long, currentDateTime: LocalDateTime): Balance {
        // 잔고 히스토리 저장
        val changedBalanceHistory = BalanceHistory (
            userId = existingBalance.userId,
            balanceHistoryId = 0,
            balanceAmount = calculatedAmount,
            createdAt = currentDateTime,
        )
        balanceRepository.saveBalanceHistory(changedBalanceHistory)

        // 잔고 변경 내역 저장
        val changedBalance = existingBalance.copy(balanceAmount = calculatedAmount, updatedAt = currentDateTime)
        balanceRepository.updateBalance(changedBalance)
        return changedBalance
    }

    /**
     *  잔고 변경
     */
    @Transactional
    fun changeBalance(userId: String, amount: Long, transactionType: TransactionType, currentDateTime: LocalDateTime): Balance {
        // 잔고 조회
        val existingBalance = retrieveBalanceForUpdate(userId) ?: Balance(userId, 0, currentDateTime, currentDateTime)

        // 변경 금액 계산
        val calculatedAmount = existingBalance.calculateChangedBalance(transactionType, amount)

        // 변경 금액 검증
        BalanceValidator.checkCalculatedAmount(calculatedAmount)

        // 잔고 히스토리 저장
        val changedBalanceHistory = BalanceHistory (
            userId = existingBalance.userId,
            balanceHistoryId = 0,
            balanceAmount = calculatedAmount,
            createdAt = currentDateTime,
        )
        balanceRepository.saveBalanceHistory(changedBalanceHistory)

        // 잔고 변경 내역 저장
        val changedBalance = existingBalance.copy(balanceAmount = calculatedAmount, updatedAt = currentDateTime)
        balanceRepository.updateBalance(changedBalance)
        return changedBalance
    }
}