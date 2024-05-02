package com.tikiticket.tickets.balance.domain

interface BalanceRepository {
    /**
     *  잔고 조회
     */
    fun findBalance(userId: String): Balance?

    /**
     *  변경을 위한 잔고 조회
     */
    fun findBalanceForUpdate(userId: String): Balance?

    /**
     *  잔고 저장
     */
    fun storeBalance(balance: Balance): Balance

    /**
     *  잔고 변경
     */
    fun changeBalance(balance: Balance)

    /**
     *  잔고 히스토리 저장
     */
    fun storeBalanceHistory(balanceHistory: BalanceHistory): BalanceHistory
}