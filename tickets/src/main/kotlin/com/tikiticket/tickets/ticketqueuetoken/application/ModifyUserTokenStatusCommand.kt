package com.tikiticket.tickets.ticketqueuetoken.application

import com.tikiticket.tickets.ticketqueuetoken.domain.TokenStatusType
import java.util.*

data class ModifyUserTokenStatusCommand (
    val userId: String,
    val tokenStatus: String
) {
    /**
     *  TokenStatusType Enum으로 변환
     */
    fun getTokenStatusTypeFromString(): TokenStatusType {
        return kotlin.runCatching {
            TokenStatusType.valueOf(tokenStatus.uppercase(Locale.getDefault()))
        }.getOrElse {
            throw TicketQueueTokenException(TicketQueueTokenError.WRONG_TOKEN_STATUS)
        }
    }
}
