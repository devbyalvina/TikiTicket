package com.tikiticket.tickets.balance.infrastructure

import com.tikiticket.tickets.appcore.infrastructure.BaseEntity
import com.tikiticket.tickets.balance.domain.BalanceHistory
import com.tikiticket.tickets.balance.domain.TransactionType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
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

    @Enumerated(EnumType.STRING)
    @NotNull
    val transactionType: TransactionType,

    @NotNull
    val balanceAmount: Long,
): BaseEntity() {
    fun toDomain(): BalanceHistory {
        return BalanceHistory(
            balanceHistoryId = this.balanceHistoryId,
            userId = this.userId,
            transactionType = this.transactionType,
            balanceAmount = this.balanceAmount,
            createdAt = this.createdAt!!
        )
    }
}