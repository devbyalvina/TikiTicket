package com.tikiticket.tickets.aggregate.balance.api.dto

import com.tikiticket.tickets.aggregate.balance.domain.TransactionType

data class ChangeBalanceRequest(
    val transactionType: TransactionType,
    val amount: Long
)