package com.tikiticket.tickets.balance.application

import com.tikiticket.tickets.balance.application.exception.BalanceValidator
import com.tikiticket.tickets.balance.domain.Balance
import com.tikiticket.tickets.balance.domain.BalanceHistory
import com.tikiticket.tickets.balance.domain.BalanceHistoryService
import com.tikiticket.tickets.balance.domain.BalanceService

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

/**
 *  API.9 잔고 변경
 */
@Component
class ChangeBalanceUseCase (
    private val balanceService: BalanceService,
    private val balanceHistoryService: BalanceHistoryService
) {
    @Transactional
    operator fun invoke(command: ChangeBalanceCommand): Balance {
        command.validate()

        val balance = balanceService.retrieveBalance(command.userId)
            ?: Balance(command.userId, 0, LocalDateTime.now(), LocalDateTime.now())

        val calculatedAmount = balance.calculateChangedBalance(command.getTransactionTypeFromString(), command.amount)
        BalanceValidator.checkCalculatedAmount(calculatedAmount)

        val changedBalance = balance.copy(balanceAmount = calculatedAmount)
        val changedBalanceHistory = BalanceHistory (
            userId = changedBalance.userId,
            balanceHistoryId = 0,
            balanceAmount = changedBalance.balanceAmount,
            createdAt = changedBalance.updatedAt,
        )
        balanceHistoryService.storeBalanceHistory(changedBalanceHistory)
        return balanceService.storeBalance(changedBalance)
    }
}