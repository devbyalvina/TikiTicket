package com.tikiticket.tickets.ticketqueuetoken.api

import com.tikiticket.tickets.ticketqueuetoken.domain.TicketQueueToken
import com.tikiticket.tickets.ticketqueuetoken.domain.TokenStatusType

data class ModifyUserTokenStatusResponse(
    val userId: String,
    val tokenStatus: TokenStatusType
){
    companion object {
        fun of(ticketQueueToken: TicketQueueToken): ModifyUserTokenStatusResponse {
            return ModifyUserTokenStatusResponse (
                userId = ticketQueueToken.userId,
                tokenStatus = ticketQueueToken.tokenStatus,
            )
        }
    }
}

