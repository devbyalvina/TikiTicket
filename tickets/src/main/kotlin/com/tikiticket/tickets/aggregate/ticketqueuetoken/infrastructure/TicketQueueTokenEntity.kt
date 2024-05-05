package com.tikiticket.tickets.aggregate.ticketqueuetoken.infrastructure

import com.tikiticket.tickets.global.infrastructure.jpa.BaseEntity
import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueToken
import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime


@Entity
@Table(name = "ticket_queue_token")
class TicketQueueTokenEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val tokenId: Long,

    @Column(unique = true)
    @NotNull
    val userId: String,

    @Enumerated(EnumType.STRING)
    @NotNull
    var tokenStatus: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType,

    @NotNull
    val expiryDateTime: LocalDateTime
) : BaseEntity() {

    fun toDomain() = com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueToken(
        id = this.tokenId,
        userId = this.userId,
        tokenStatus = this.tokenStatus,
        expiryDateTime = this.expiryDateTime,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )

    companion object {
        fun of(ticketQueueToken: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueToken): com.tikiticket.tickets.aggregate.ticketqueuetoken.infrastructure.TicketQueueTokenEntity {
            return com.tikiticket.tickets.aggregate.ticketqueuetoken.infrastructure.TicketQueueTokenEntity(
                tokenId = ticketQueueToken.id,
                userId = ticketQueueToken.userId,
                tokenStatus = ticketQueueToken.tokenStatus,
                expiryDateTime = ticketQueueToken.expiryDateTime,
            )
        }
    }
}