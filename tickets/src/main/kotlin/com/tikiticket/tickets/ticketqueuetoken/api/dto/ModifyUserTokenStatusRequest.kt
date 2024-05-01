package com.tikiticket.tickets.ticketqueuetoken.api.dto

import com.tikiticket.tickets.ticketqueuetoken.domain.TokenStatusType

data class ModifyUserTokenStatusRequest(
    val tokenStatus: TokenStatusType
)
