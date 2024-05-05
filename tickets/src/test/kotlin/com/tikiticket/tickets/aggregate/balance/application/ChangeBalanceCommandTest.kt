package com.tikiticket.tickets.aggregate.balance.application

import com.tikiticket.tickets.aggregate.balance.application.ChangeBalanceCommand
import com.tikiticket.tickets.global.domain.exception.CustomException
import com.tikiticket.tickets.aggregate.balance.domain.BalanceError
import com.tikiticket.tickets.aggregate.balance.domain.TransactionType
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class ChangeBalanceCommandTest {
    @Test
    fun `amount가 양수면 예외를 반환하지 않는다`() {
        // Given
        val command = ChangeBalanceCommand("user123", TransactionType.CHARGE, 100L)

        // When & Then
        assertDoesNotThrow {
            command.validate()
        }
    }

    @Test
    fun `amount가 0이면 INVALID_AMOUNT_PARAMETER exception을 반환한다`() {
        // Given & When & Then
        val exception = assertThrows(CustomException::class.java) {
            val command = ChangeBalanceCommand("user123", TransactionType.CHARGE, 0L)
        }
        assertEquals(BalanceError.INVALID_AMOUNT_PARAMETER, exception.customError)
    }

    @Test
    fun `amount가 음수면 INVALID_AMOUNT_PARAMETER exception을 반환한다`() {
        // Given & When & Then
        val exception = assertThrows(CustomException::class.java) {
            val command = ChangeBalanceCommand("user123", TransactionType.CHARGE, -100L)
        }
        assertEquals(BalanceError.INVALID_AMOUNT_PARAMETER, exception.customError)
    }
}