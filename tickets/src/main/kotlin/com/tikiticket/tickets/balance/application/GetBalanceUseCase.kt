package com.tikiticket.tickets.balance.application

import com.tikiticket.tickets.balance.application.exception.BalanceError
import com.tikiticket.tickets.balance.application.exception.BalanceException
import com.tikiticket.tickets.balance.domain.Balance
import com.tikiticket.tickets.balance.domain.BalanceService
import org.springframework.stereotype.Component

/**
 *  API.9 잔고 조회
 */
@Component
class GetBalanceUseCase(
    private val balanceService: BalanceService
) {
    operator fun invoke(userId: String): Balance {
        val balance = balanceService.retrieveBalance(userId) ?: throw BalanceException(BalanceError.BALANCE_NOT_FOUND)
        return balance
    }
}