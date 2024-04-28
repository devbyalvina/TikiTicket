package com.tikiticket.tickets.concert.application

import com.tikiticket.tickets.appcore.domain.exception.CustomException
import com.tikiticket.tickets.concert.domain.Concert
import com.tikiticket.tickets.concert.domain.ConcertError
import com.tikiticket.tickets.concert.domain.ConcertService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetConcertWithSeatsUseCaseTest {

    @Test
    fun `콘서트가 없으면 CustomException을 반환한다`() {
        // Given
        val concertId = 1L
        val concertService = mockk<ConcertService> {
            every { findConcertWithSeats(concertId) } returns null
        }
        val useCase = GetConcertWithSeatsUseCase(concertService)

        // When & Then
        val exception = assertThrows<CustomException> { useCase(concertId) }
        assertEquals(ConcertError.CONCERT_NOT_FOUND, exception.customError)
    }

    @Test
    fun `콘서트 좌석 목록이 없으면 CustomException을 반환한다`() {
        // Given
        val concertId = 1L
        val concert = mockk<Concert> {
            every { seats } returns null
        }
        val concertService = mockk<ConcertService> {
            every { findConcertWithSeats(concertId) } returns concert
        }
        val useCase = GetConcertWithSeatsUseCase(concertService)

        // When & Then
        val exception = assertThrows<CustomException> { useCase(concertId) }
        assertEquals(ConcertError.CONCERT_SEATS_NOT_FOUND, exception.customError)
    }

    @Test
    fun `콘서트 좌석 목록이 있으면 콘서트를 반환한다`() {
        // Given
        val concertId = 1L
        val concert = mockk<Concert> {
            every { seats } returns listOf(mockk())
        }
        val concertService = mockk<ConcertService> {
            every { findConcertWithSeats(concertId) } returns concert
        }
        val useCase = GetConcertWithSeatsUseCase(concertService)

        // When
        val result = useCase(concertId)

        // Then
        assertEquals(concert, result)
    }
}