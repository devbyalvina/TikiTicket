package com.tikiticket.tickets.balance.application

import com.tikiticket.tickets.balance.domain.Balance
import com.tikiticket.tickets.balance.domain.BalanceService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

/**
 *  API.10 잔고 변경
 */
@Component
class ChangeBalanceUseCase (
    private val balanceService: BalanceService,
) {
    @Transactional
    operator fun invoke(command: ChangeBalanceCommand): Balance {
        val currentDateTime = LocalDateTime.now()
        val transactionType = command.getTransactionTypeFromString()

        // 잔고 변경
        val changedBalance = balanceService.changeBalance(command.userId, command.amount, transactionType, currentDateTime)

        return changedBalance
    }
}