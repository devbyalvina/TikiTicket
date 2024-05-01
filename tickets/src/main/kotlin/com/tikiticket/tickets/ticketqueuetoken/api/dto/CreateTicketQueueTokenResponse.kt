package com.tikiticket.tickets.ticketqueuetoken.api.dto

import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueToken
import com.tikiticket.tickets.ticketqueuetoken.domain.TokenStatusType
import java.time.LocalDateTime

data class CreateTicketQueueTokenResponse(
    val tokenId: Long,
    val userId: String,
    val tokenStatus: TokenStatusType,
    val expiryDateTime: LocalDateTime,
) {
    companion object {
        fun of(ticketQueueToken: TicketQueueToken): CreateTicketQueueTokenResponse {
            return CreateTicketQueueTokenResponse (
                tokenId = ticketQueueToken.id,
                userId = ticketQueueToken.userId,
                tokenStatus = ticketQueueToken.tokenStatus,
                expiryDateTime = ticketQueueToken.expiryDateTime,
            )
        }
    }
}
