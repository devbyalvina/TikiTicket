package com.tikiticket.tickets.balance.application

import com.tikiticket.tickets.appcore.domain.exception.CustomException
import com.tikiticket.tickets.balance.domain.BalanceError
import com.tikiticket.tickets.balance.domain.TransactionType
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class ChangeBalanceCommandTest {
    @Test
    fun `amount가 양수면 예외를 반환하지 않는다`() {
        // Given
        val command = ChangeBalanceCommand("user123", "CHARGE", 100L)

        // When & Then
        assertDoesNotThrow {
            command.validate()
        }
    }

    @Test
    fun `amount가 0이면 INVALID_AMOUNT_PARAMETER exception을 반환한다`() {
        // Given & When & Then
        val exception = assertThrows(CustomException::class.java) {
            val command = ChangeBalanceCommand("user123", "CHARGE", 0L)
        }
        assertEquals(BalanceError.INVALID_AMOUNT_PARAMETER, exception.customError)
    }

    @Test
    fun `amount가 음수면 INVALID_AMOUNT_PARAMETER exception을 반환한다`() {
        // Given & When & Then
        val exception = assertThrows(CustomException::class.java) {
            val command = ChangeBalanceCommand("user123", "CHARGE", -100L)
        }
        assertEquals(BalanceError.INVALID_AMOUNT_PARAMETER, exception.customError)
    }

    @Test
    fun `transactionType이 TransactionType의 이름이 매칭되면 매칭되는 TransactionType 값을 반환한다`() {
        // Given
        val command = ChangeBalanceCommand("user123", "CHARGE", 100L)

        // When
        val transactionType = command.getTransactionTypeFromString()

        // Then
        assertEquals(TransactionType.CHARGE, transactionType)
    }

    @Test
    fun `transactionType이 TransactionType의 이름과 매칭되는 값이 없으면 WRONG_TRANSACTION_TYPE exception을 반환한다`() {
        // Given
        val command = ChangeBalanceCommand("user123", "INVALID_TYPE", 100L)

        // When & Then
        val exception = assertThrows(CustomException::class.java) {
            command.getTransactionTypeFromString()
        }
        assertEquals(BalanceError.WRONG_TRANSACTION_TYPE, exception.customError)
    }
}