package com.tikiticket.tickets.balance.application

import com.tikiticket.tickets.balance.domain.Balance
import com.tikiticket.tickets.balance.domain.BalanceService
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 *  API.10 잔고 변경
 */
@Component
class ChangeBalanceUseCase (
    private val balanceService: BalanceService,
) {
    operator fun invoke(command: ChangeBalanceCommand): Balance {
        // 잔고 변경
        val currentDateTime = LocalDateTime.now()
        val changedBalance = balanceService.changeBalance(command.userId, command.amount, command.transactionType, currentDateTime)

        return changedBalance
    }
}