package com.tikiticket.tickets.balance.infrastructure

import com.tikiticket.tickets.appcore.infrastructure.BaseEntity
import com.tikiticket.tickets.balance.domain.Balance
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "balance")
class BalanceEntity(
    @Id
    @NotNull
    val userId: String,

    @NotNull
    val balanceAmount: Long,
): BaseEntity() {
    fun toDomain(): Balance {
        return Balance(
            userId = this.userId,
            balanceAmount = this.balanceAmount,
            createdAt = this.createdAt!!,
            updatedAt = this.updatedAt!!
        )
    }
}