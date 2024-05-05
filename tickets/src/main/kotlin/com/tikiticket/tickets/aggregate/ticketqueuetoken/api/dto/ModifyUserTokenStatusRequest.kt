package com.tikiticket.tickets.aggregate.ticketqueuetoken.api.dto

import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType

data class ModifyUserTokenStatusRequest(
    val tokenStatus: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType
)
