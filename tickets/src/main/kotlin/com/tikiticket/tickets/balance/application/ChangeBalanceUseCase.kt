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
        // 잔고 조회
        val existingBalance = balanceService.retrieveBalance(command.userId) ?: Balance(command.userId, 0, currentDateTime, currentDateTime)
        // 변경 금액 계산
        val calculatedAmount = existingBalance.calculateChangedBalance(transactionType, command.amount)
        // 변경 금액 검증
        ChangeBalanceValidator.checkCalculatedAmount(calculatedAmount)
        // 잔고 변경
        val changedBalance = balanceService.changeBalance(existingBalance, transactionType, calculatedAmount, currentDateTime)

        return changedBalance
    }
}