package com.tikiticket.tickets.balance.domain

interface BalanceRepository {
    /**
     *  잔고 조회
     */
    fun findBalanceByUserId(userId: String): Balance?

    /**
     *  변경을 위한 잔고 조회
     */
    fun findBalanceByUserIdForUpdate(userId: String): Balance?

    /**
     *  잔고 저장
     */
    fun saveBalance(balance: Balance): Balance

    /**
     *  잔고 변경
     */
    fun updateBalance(balance: Balance)
    /**
     *  잔고 히스토리 저장
     */
    fun saveBalanceHistory(balanceHistory: BalanceHistory): BalanceHistory
}