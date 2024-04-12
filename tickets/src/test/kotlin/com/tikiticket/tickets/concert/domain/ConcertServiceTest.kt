package com.tikiticket.tickets.concert.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class ConcertServiceTest {
    @InjectMocks
    private lateinit var concertService: ConcertService

    @Mock
    private lateinit var concertRepository: ConcertRepository

    /**
     * API.4. 콘서트 스케줄 목록 조회
     * [Test 4-1] Success
     */
    @Test
    fun `날짜 조건으로 콘서트 스케줄 목록을 조회한다`() {
        // Given
        val startDate = LocalDate.of(2024, 4, 1)
        val endDate = LocalDate.of(2024, 4, 10)
        val concerts = listOf(
            Concert(
                id = 1,
                concertName = "콘서트 1",
                artistName = "가수 1",
                concertDate = LocalDateTime.of(2024, 4, 5, 18, 0),
                venue = "장소 1",
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                seats = null
            ),
            Concert(
                id = 2,
                concertName = "콘서트 2",
                artistName = "가수 2",
                concertDate = LocalDateTime.of(2024, 4, 8, 19, 0),
                venue = "장소 2",
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                seats = null
            )
        )
        given(concertRepository.findConcertsByDateRange(startDate, endDate)).willReturn(concerts)

        // When
        val result = concertService.findConcertsByDateRange(startDate, endDate)

        // Then
        assertEquals(2, result?.size)
        assertEquals("콘서트 1", result!![0].concertName)
        assertEquals("가수 1", result[0].artistName)
        assertEquals(LocalDateTime.of(2024, 4, 5, 18, 0), result[0].concertDate)
        assertEquals("장소 1", result[0].venue)
        assertEquals("콘서트 2", result[1].concertName)
        assertEquals("가수 2", result[1].artistName)
        assertEquals(LocalDateTime.of(2024, 4, 8, 19, 0), result[1].concertDate)
        assertEquals("장소 2", result[1].venue)
    }

    /**
     * API.5. 콘서트 좌석 목록 조회
     * [Test 5-1] Success
     */
    @Test
    fun `콘서트 ID를 입력 받아 콘서트 좌석 목록을 조회한다`() {
        // Given
        val concertId = 1L
        val concertSeats = listOf(
            ConcertSeat(concertId, 1, SeatStatusType.AVAILABLE, 10000, LocalDateTime.now(), LocalDateTime.now()),
            ConcertSeat(concertId, 2, SeatStatusType.BOOKED, 10000, LocalDateTime.now(), LocalDateTime.now())
        )
        val concert = Concert(
            id = concertId,
            concertName = "콘서트 1",
            artistName = "가수 1",
            concertDate = LocalDateTime.of(2024, 4, 15, 19, 0),
            venue = "장소 1",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            seats = concertSeats
        )
        given(concertRepository.findConcertWithSeatsById(concertId)).willReturn(concert)

        // When
        val result = concertService.findConcertWithSeatsById(concertId)

        // Then
        assertEquals(concert, result)
    }
}
