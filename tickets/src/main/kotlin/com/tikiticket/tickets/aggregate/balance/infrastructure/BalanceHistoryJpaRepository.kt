package com.tikiticket.tickets.aggregate.balance.infrastructure

import org.springframework.data.jpa.repository.JpaRepository

interface BalanceHistoryJpaRepository: JpaRepository<BalanceHistoryEntity, Long> {
}