package com.tikiticket.tickets.concert.application

import com.tikiticket.tickets.appcore.domain.exception.CustomException
import com.tikiticket.tickets.concert.domain.ConcertError
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.time.LocalDate

class GetConcertsByDateRangeCommandTest {
    @Test
    fun `시작일이 종료일보다 이전인 경우 예외가 발생하지 않는다`() {
        // Given
        val startDate = LocalDate.of(2024, 1, 1)
        val endDate = LocalDate.of(2024, 1, 2)
        val command = GetConcertsByDateRangeCommand(startDate, endDate)

        // When
        command.validate()

        // Then: 예외가 발생하지 않아야 함
    }

    @Test
    fun `시작일이 종료일과 같은 경우 예외가 발생하지 않는다`() {
        // Given
        val date = LocalDate.of(2024, 1, 1)
        val command = GetConcertsByDateRangeCommand(date, date)

        // When
        command.validate()

        // Then: 예외가 발생하지 않아야 함
    }

    @Test
    fun `시작일이 종료일보다 늦은 경우 예외가 발생한다`() {
        // Given
        val startDate = LocalDate.of(2024, 1, 2)
        val endDate = LocalDate.of(2024, 1, 1)

        // When & Then: CustomException 예외가 발생해야 함
        val exception = assertThrows(CustomException::class.java) {
            val command = GetConcertsByDateRangeCommand(startDate, endDate)
        }
        assertEquals(ConcertError.INVALID_DATE_RANGE_PARAMETER, exception.customError)
    }
}