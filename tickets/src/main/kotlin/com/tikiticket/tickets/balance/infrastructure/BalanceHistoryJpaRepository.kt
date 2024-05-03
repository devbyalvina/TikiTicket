package com.tikiticket.tickets.balance.infrastructure

import org.springframework.data.jpa.repository.JpaRepository

interface BalanceHistoryJpaRepository: JpaRepository<BalanceHistoryEntity, Long> {
}