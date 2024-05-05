package com.tikiticket.tickets.aggregate.balance.infrastructure

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

interface BalanceJpaRepository: JpaRepository<BalanceEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from BalanceEntity b where b.userId = :userId")
    fun findBalanceForUpdateById(userId: String): BalanceEntity?
}