package com.tikiticket.tickets.ticketqueuetoken.application

import com.tikiticket.tickets.ticketqueuetoken.domain.TokenStatusType
import java.util.*

data class ModifyUserTokenStatusCommand (
    val userId: String,
    val tokenStatus: TokenStatusType
)
