package com.tikiticket.tickets.aggregate.ticketqueuetoken.application

import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType

data class ModifyUserTokenStatusCommand (
    val userId: String,
    val tokenStatus: TokenStatusType
)
