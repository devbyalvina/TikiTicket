package com.tikiticket.tickets.aggregate.balance.domain

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class BalanceService (
    private val balanceRepository: BalanceRepository,
){
    private val logger = LoggerFactory.getLogger(javaClass)
    /**
     *  잔고 조회
     */
    fun retrieveBalance(userId: String): Balance? {
        val balance = balanceRepository.findBalance(userId)
        return balance
    }

    /**
     *  잔고 저장
     */
    fun storeBalance(balance: Balance): Balance {
        return balanceRepository.storeBalance(balance);
    }

    /**
     *  잔고 변경
     */
    @Transactional
    fun changeBalance(userId: String, amount: Long, transactionType: TransactionType, currentDateTime: LocalDateTime): Balance {
        // 잔고 조회
        val existingBalance = balanceRepository.findBalanceForUpdate(userId) ?: Balance(0, userId, 0, currentDateTime, currentDateTime)

        // 변경 금액 계산
        val calculatedAmount = existingBalance.calculateChangedBalance(transactionType, amount)

        // 변경 금액 검증
        BalanceValidator.checkCalculatedAmount(calculatedAmount)

        // 잔고 변경 내역 저장
        val changedBalance = existingBalance.copy(balanceAmount = calculatedAmount, updatedAt = currentDateTime)
        //logger.info("!!!!!!TEST[changedBalance]!!!!!!!$changedBalance")
        val storedBalance = balanceRepository.storeBalance(changedBalance)
        //logger.info("!!!!!!TEST[storedBalance]!!!!!!!$storedBalance")

        // 잔고 히스토리 저장
        val changedBalanceHistory = BalanceHistory (
            userId = storedBalance.userId,
            balanceHistoryId = 0,
            balanceId = storedBalance.id,
            transactionType = transactionType,
            balanceAmount = calculatedAmount,
            createdAt = currentDateTime,
        )
        balanceRepository.storeBalanceHistory(changedBalanceHistory)

        return storedBalance
    }
}