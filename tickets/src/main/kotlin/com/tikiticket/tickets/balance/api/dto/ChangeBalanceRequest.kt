package com.tikiticket.tickets.balance.api.dto

import com.tikiticket.tickets.balance.domain.TransactionType

data class ChangeBalanceRequest(
    val transactionType: TransactionType,
    val amount: Long
)