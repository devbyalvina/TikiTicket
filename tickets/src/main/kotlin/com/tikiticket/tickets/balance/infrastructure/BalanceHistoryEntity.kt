package com.tikiticket.tickets.balance.infrastructure

import com.tikiticket.tickets.`app-core`.infrastructure.BaseEntity
import com.tikiticket.tickets.balance.domain.BalanceHistory
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "balance_history")
class BalanceHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val balanceHistoryId: Long,

    @NotNull
    val userId: String,

    @NotNull
    val balanceAmount: Long,
): BaseEntity() {
    fun toDomain(): BalanceHistory {
        return BalanceHistory(
            balanceHistoryId = this.balanceHistoryId,
            userId = this.userId,
            balanceAmount = this.balanceAmount,
            createdAt = this.createdAt!!
        )
    }
}