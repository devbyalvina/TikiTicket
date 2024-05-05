package com.tikiticket.tickets.aggregate.ticketqueuetoken.application

import com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType
import java.util.*

data class ModifyUserTokenStatusCommand (
    val userId: String,
    val tokenStatus: com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TokenStatusType
)
