package com.tikiticket.tickets.aggregate.balance.infrastructure

import com.tikiticket.tickets.global.infrastructure.jpa.BaseEntity
import com.tikiticket.tickets.aggregate.balance.domain.Balance
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "balance")
class BalanceEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val balanceId: Long,

    @Column(unique = true)
    @NotNull
    val userId: String,

    @NotNull
    val balanceAmount: Long,
): BaseEntity() {
    fun toDomain(): Balance {
        return Balance(
            id = this.balanceId,
            userId = this.userId,
            balanceAmount = this.balanceAmount,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }

    companion object {
        fun of(balance: Balance): BalanceEntity {
            return BalanceEntity (
                balanceId = balance.id,
                userId = balance.userId,
                balanceAmount = balance.balanceAmount,
            )
        }
    }
}