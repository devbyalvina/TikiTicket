package com.tikiticket.tickets.aggregate.ticketqueuetoken.api.dto

import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueToken
import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType
import java.time.LocalDateTime

data class CreateTicketQueueTokenResponse(
    val tokenId: Long,
    val userId: String,
    val tokenStatus: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType,
    val expiryDateTime: LocalDateTime,
) {
    companion object {
        fun of(ticketQueueToken: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueToken): com.tikiticket.tickets.aggregate.ticketqueuetoken.api.dto.CreateTicketQueueTokenResponse {
            return com.tikiticket.tickets.aggregate.ticketqueuetoken.api.dto.CreateTicketQueueTokenResponse(
                tokenId = ticketQueueToken.id,
                userId = ticketQueueToken.userId,
                tokenStatus = ticketQueueToken.tokenStatus,
                expiryDateTime = ticketQueueToken.expiryDateTime,
            )
        }
    }
}
