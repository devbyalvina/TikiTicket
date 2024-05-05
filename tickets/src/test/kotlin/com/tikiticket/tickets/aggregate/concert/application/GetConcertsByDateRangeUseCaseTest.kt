package com.tikiticket.tickets.aggregate.concert.application

import com.tikiticket.tickets.aggregate.concert.domain.Concert
import com.tikiticket.tickets.aggregate.concert.domain.ConcertError
import com.tikiticket.tickets.aggregate.concert.domain.ConcertService
import com.tikiticket.tickets.global.domain.exception.CustomException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

class GetConcertsByDateRangeUseCaseTest {
    @Test
    fun `기간 조건에 대한 콘서트가 있는 경우 콘서트 목록을 조회한다`() {
        // Given
        val startDate = LocalDate.of(2024, 1, 1)
        val endDate = LocalDate.of(2024, 1, 3)
        val concerts = listOf(
            Concert(1, "Concert 1", "Artist 1", LocalDateTime.now(), "Venue 1", LocalDateTime.now(), LocalDateTime.now(), null),
            Concert(2, "Concert 2", "Artist 2", LocalDateTime.now(), "Venue 2", LocalDateTime.now(), LocalDateTime.now(), null)
        )
        val concertService = mockk<ConcertService> {
            every { findConcertsByDateRange(startDate, endDate) } returns concerts
        }
        val useCase = GetConcertsByDateRangeUseCase(concertService)
        val command = GetConcertsByDateRangeCommand(startDate, endDate)

        // When
        val result = useCase(command)

        // Then
        assertEquals(concerts, result)
    }

    @Test
    fun `기간 조건에 대한 콘서트가 없는 경우 예외를 반환한다`() {
        // Given
        val startDate = LocalDate.of(2024, 1, 1)
        val endDate = LocalDate.of(2024, 1, 3)
        val command = GetConcertsByDateRangeCommand(startDate, endDate)

        val concertService = mockk<ConcertService>()
        every { concertService.findConcertsByDateRange(startDate, endDate) } returns null

        val useCase = GetConcertsByDateRangeUseCase(concertService)

        // When & Then: CustomException 예외가 발생해야 함
        val exception = assertThrows(CustomException::class.java) {
            useCase(command)
        }
        assertEquals(ConcertError.CONCERT_NOT_FOUND, exception.customError)
    }
}