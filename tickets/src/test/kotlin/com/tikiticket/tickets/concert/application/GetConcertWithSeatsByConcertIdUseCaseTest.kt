package com.tikiticket.tickets.concert.application

import com.tikiticket.tickets.concert.application.exception.ConcertError
import com.tikiticket.tickets.concert.application.exception.ConcertException
import com.tikiticket.tickets.concert.domain.Concert
import com.tikiticket.tickets.concert.domain.ConcertService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetConcertWithSeatsByConcertIdUseCaseTest {

    @Test
    fun `콘서트가 없으면 ConcertException을 반환한다`() {
        // Given
        val concertId = 1L
        val concertService = mockk<ConcertService> {
            every { findConcertWithSeats(concertId) } returns null
        }
        val useCase = GetConcertWithSeatsByConcertIdUseCase(concertService)

        // When & Then
        val exception = assertThrows<ConcertException> { useCase(concertId) }
        assertEquals(ConcertError.CONCERT_NOT_FOUND, exception.error)
    }

    @Test
    fun `콘서트 좌석 목록이 없으면 ConcertException을 반환한다`() {
        // Given
        val concertId = 1L
        val concert = mockk<Concert> {
            every { seats } returns null
        }
        val concertService = mockk<ConcertService> {
            every { findConcertWithSeats(concertId) } returns concert
        }
        val useCase = GetConcertWithSeatsByConcertIdUseCase(concertService)

        // When & Then
        assertThrows<ConcertException> { useCase(concertId) }
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
        val useCase = GetConcertWithSeatsByConcertIdUseCase(concertService)

        // When
        val result = useCase(concertId)

        // Then
        assertEquals(concert, result)
    }
}