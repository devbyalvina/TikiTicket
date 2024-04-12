package com.tikiticket.tickets.balance.application

import com.tikiticket.tickets.balance.application.exception.BalanceValidator
import com.tikiticket.tickets.balance.domain.Balance
import com.tikiticket.tickets.balance.domain.BalanceService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 *  API.9 잔고 변경
 */
@Component
class ChangeBalanceUseCase (
    private val balanceService: BalanceService
) {
    @Transactional
    operator fun invoke(command: ChangeBalanceCommand): Balance {
        command.validate()

        val balance = balanceService.retrieveBalance(command.userId)
            ?: Balance(command.userId, 0, LocalDateTime.now(), LocalDateTime.now())

        val calculatedAmount = balance.calculateChangedBalance(command.getTransactionTypeFromString(), command.amount)
        BalanceValidator.checkCalculatedAmount(calculatedAmount)

        val changedBalance = balance.copy(balanceAmount = calculatedAmount)

        return balanceService.storeBalance(changedBalance)
    }
}