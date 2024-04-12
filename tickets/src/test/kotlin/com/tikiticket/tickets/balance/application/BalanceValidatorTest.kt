package com.tikiticket.tickets.balance.application

import com.tikiticket.tickets.balance.application.exception.BalanceError
import com.tikiticket.tickets.balance.application.exception.BalanceException
import com.tikiticket.tickets.balance.application.exception.BalanceValidator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class BalanceValidatorTest {
    @Test
    fun `계산된 금액이 양수가 아니면 BalanceError를 반환한다`() {
        // Given
        val calculatedAmount = -100L

        // When & Then
        assertThrows(BalanceException::class.java) {
            BalanceValidator.checkCalculatedAmount(calculatedAmount)
        }.also {
            assertEquals(BalanceError.INSUFFICIENT_BALANCE, it.error)
        }
    }

    @Test
    fun `계산된 금액이 양수면 에러가 발생하지 않는다`() {
        // Given
        val calculatedAmount = 100L

        // When & Then
        assertDoesNotThrow {
            BalanceValidator.checkCalculatedAmount(calculatedAmount)
        }
    }
}