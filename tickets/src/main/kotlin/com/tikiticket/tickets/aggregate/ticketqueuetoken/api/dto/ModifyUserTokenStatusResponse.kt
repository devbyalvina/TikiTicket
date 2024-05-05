package com.tikiticket.tickets.aggregate.ticketqueuetoken.api.dto

import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueToken
import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType

data class ModifyUserTokenStatusResponse(
    val userId: String,
    val tokenStatus: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType
){
    companion object {
        fun of(ticketQueueToken: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueToken): com.tikiticket.tickets.aggregate.ticketqueuetoken.api.dto.ModifyUserTokenStatusResponse {
            return com.tikiticket.tickets.aggregate.ticketqueuetoken.api.dto.ModifyUserTokenStatusResponse(
                userId = ticketQueueToken.userId,
                tokenStatus = ticketQueueToken.tokenStatus,
            )
        }
    }
}

