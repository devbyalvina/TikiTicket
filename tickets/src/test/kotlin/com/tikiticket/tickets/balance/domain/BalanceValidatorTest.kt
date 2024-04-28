package com.tikiticket.tickets.balance.domain

import com.tikiticket.tickets.appcore.domain.exception.CustomException
import com.tikiticket.tickets.appcore.domain.log.LogLevel
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BalanceValidatorTest {

    @Test
    fun `계산된 금액이 양수면 예외를 반환하지 않는다`() {
        // Given
        val calculatedAmount = 100L

        // When
        BalanceValidator.checkCalculatedAmount(calculatedAmount)

        // Then: No exception should be thrown
    }

    @Test
    fun `계산된 금액이 양수가 아니면 예외를 반환한다`() {
        // Given
        val calculatedAmount = -50L
        val calculatedAmount2 = 0L

        // When, Then
        val exception = assertThrows<CustomException> { BalanceValidator.checkCalculatedAmount(calculatedAmount) }
        val exception2 = assertThrows<CustomException> { BalanceValidator.checkCalculatedAmount(calculatedAmount2) }

        // Then
        assert(exception.logLevel == LogLevel.INFO)
        assert(exception.customError == BalanceError.INSUFFICIENT_BALANCE)
        assert(exception2.logLevel == LogLevel.INFO)
        assert(exception2.customError == BalanceError.INSUFFICIENT_BALANCE)
    }

}