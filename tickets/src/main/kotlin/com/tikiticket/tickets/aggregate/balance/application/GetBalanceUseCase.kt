package com.tikiticket.tickets.aggregate.balance.application

import com.tikiticket.tickets.global.domain.exception.CustomException
import org.springframework.boot.logging.LogLevel
import com.tikiticket.tickets.aggregate.balance.domain.Balance
import com.tikiticket.tickets.aggregate.balance.domain.BalanceError
import com.tikiticket.tickets.aggregate.balance.domain.BalanceService
import org.springframework.stereotype.Component

/**
 *  API.9 잔고 조회
 */
@Component
class GetBalanceUseCase(
    private val balanceService: BalanceService
) {
    operator fun invoke(userId: String): Balance {
        val balance = balanceService.retrieveBalance(userId) ?: throw CustomException(LogLevel.INFO, BalanceError.BALANCE_NOT_FOUND)
        return balance
    }
}