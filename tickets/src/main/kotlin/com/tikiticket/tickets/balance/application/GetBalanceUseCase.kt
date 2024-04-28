package com.tikiticket.tickets.balance.application

import com.tikiticket.tickets.appcore.domain.exception.CustomException
import com.tikiticket.tickets.appcore.domain.log.LogLevel
import com.tikiticket.tickets.balance.domain.Balance
import com.tikiticket.tickets.balance.domain.BalanceError
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
        val balance = balanceService.retrieveBalance(userId) ?: throw CustomException(LogLevel.INFO, BalanceError.BALANCE_NOT_FOUND)
        return balance
    }
}