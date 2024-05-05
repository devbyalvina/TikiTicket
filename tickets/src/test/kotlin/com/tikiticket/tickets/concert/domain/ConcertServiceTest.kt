package com.tikiticket.tickets.concert.domain

import com.tikiticket.tickets.appcore.domain.exception.CustomException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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

    @Test
    fun `콘서트 ID를 입력 받아 콘서트 좌석 목록을 조회한다`() {
        // Given
        val concertId = 1L
        val concertSeats = listOf(
            ConcertSeat(1, concertId, 1, SeatStatusType.AVAILABLE, 10000, LocalDateTime.now(), LocalDateTime.now()),
            ConcertSeat(2, concertId, 2, SeatStatusType.BOOKED, 10000, LocalDateTime.now(), LocalDateTime.now())
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
        given(concertRepository.findConcert(concertId)).willReturn(concert.copy(seats = null))
        given(concertRepository.findConcertSeats(concertId)).willReturn(concertSeats)

        // When
        val result = concertService.findConcertWithSeats(concertId)

        // Then
        assertEquals(concert, result)
    }

    @Test
    fun `콘서트 좌석을 조회한다`() {
        // Given
        val concertId = 1L
        val expectedConcert = Concert(
            id = concertId,
            concertName = "Concert 1",
            artistName = "Artist 1",
            concertDate = LocalDateTime.now(),
            venue = "Venue 1",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            seats = emptyList() // 빈 좌석 목록으로 초기화
        )
        val concertRepository = mockk<ConcertRepository> {
            every { findConcert(concertId) } returns expectedConcert
        }
        val concertService = ConcertService(concertRepository)

        // When
        val actualConcert = concertService.findConcert(concertId)

        // Then
        assertEquals(expectedConcert, actualConcert)
    }


    @Test
    fun `콘서트 좌석을 수정한다`() {
        // Given
        val concertSeat = ConcertSeat(
            id = 1L,
            concertId = 1L,
            seatNo = 1L,
            seatStatus = SeatStatusType.BOOKED,
            ticketPrice = 10000L,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        val concertRepository = mockk<ConcertRepository>(relaxed = true)
        val concertService = ConcertService(concertRepository)

        // When
        concertService.updateConcertSeat(concertSeat)

        // Then
        verify(exactly = 1) { concertRepository.updateConcertSeat(concertSeat) }
    }

    @Test
    fun `비관적락을 사용하여 콘서트 좌석 상태를 변경한다`() {
        // Given
        val concertSeatId = 1L
        val concertId = 1L
        val concertSeat = ConcertSeat(
            id = concertSeatId,
            concertId = concertId,
            seatNo = 1L,
            seatStatus = SeatStatusType.AVAILABLE,
            ticketPrice = 100L,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        val concertRepository = mockk<ConcertRepository>()
        every { concertRepository.findConcertSeatForUpdate(concertSeatId) } returns concertSeat
        every { concertRepository.storeConcertSeat(any()) } returns concertSeat.copy(seatStatus = SeatStatusType.BOOKED, updatedAt = LocalDateTime.now())

        val concertService = ConcertService(concertRepository)

        // When
        concertService.changeConcertSeatStatusWithPessimisticLock(concertSeatId, concertId, SeatStatusType.AVAILABLE, SeatStatusType.BOOKED)

        // Then
        verify(exactly = 1) { concertRepository.findConcertSeatForUpdate(concertSeatId) }
        verify(exactly = 1) { concertRepository.storeConcertSeat(any()) }
    }

    @Test
    fun `비관적락을 사용하여 콘서트 좌석 상태를 변경 시 콘서트 좌석이 존재하지 않으면 에러를 반환한다`() {
        // Given
        val concertSeatId = 1L
        val concertId = 1L

        val concertRepository = mockk<ConcertRepository>()
        every { concertRepository.findConcertSeatForUpdate(concertSeatId) } returns null

        val concertService = ConcertService(concertRepository)

        // When, Then
        val exception = assertThrows<CustomException> {
            concertService.changeConcertSeatStatusWithPessimisticLock(concertSeatId, concertId, SeatStatusType.AVAILABLE, SeatStatusType.BOOKED)
        }
        assert(exception.customError == ConcertError.CONCERT_SEAT_NOT_FOUND)
    }

    @Test
    fun `비관적락을 사용하여 변경하려는 콘서트 좌석 상태가 AVAILABLE이 아니면 예외를 반환한다`() {
        // Given
        val concertId = 1L
        val concertSeatId = 1L
        val concertSeat = ConcertSeat(
            id = concertSeatId,
            concertId = concertId,
            seatNo = 1L,
            seatStatus = SeatStatusType.BOOKED,
            ticketPrice = 100L,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        val concertRepository = mockk<ConcertRepository>()
        every { concertRepository.findConcertSeatForUpdate(concertSeatId) } returns concertSeat

        val concertService = ConcertService(concertRepository)

        // When, Then
        val exception = assertThrows<CustomException> {
            concertService.changeConcertSeatStatusWithPessimisticLock(concertSeatId, concertId, SeatStatusType.AVAILABLE, SeatStatusType.BOOKED)
        }
        assert(exception.customError == ConcertError.SEAT_NOT_AVAILABLE)
    }

    @Test
    fun `콘서트 좌석 상태를 변경한다`() {
        // Given
        val concertSeatId = 1L
        val concertId = 1L
        val concertSeat = ConcertSeat(
            id = concertSeatId,
            concertId = concertId,
            seatNo = 1L,
            seatStatus = SeatStatusType.AVAILABLE,
            ticketPrice = 100L,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        val concertRepository = mockk<ConcertRepository>()
        every { concertRepository.findConcertSeat(concertSeatId) } returns concertSeat
        every { concertRepository.storeConcertSeat(any()) } returns concertSeat.copy(seatStatus = SeatStatusType.BOOKED, updatedAt = LocalDateTime.now())

        val concertService = ConcertService(concertRepository)

        // When
        concertService.changeConcertSeatStatus(concertSeatId, concertId, SeatStatusType.AVAILABLE, SeatStatusType.BOOKED)

        // Then
        verify(exactly = 1) { concertRepository.findConcertSeat(concertSeatId) }
        verify(exactly = 1) { concertRepository.storeConcertSeat(any()) }
    }

    @Test
    fun `콘서트 좌석 상태를 변경 시 콘서트 좌석이 존재하지 않으면 에러를 반환한다`() {
        // Given
        val concertSeatId = 1L
        val concertId = 1L

        val concertRepository = mockk<ConcertRepository>()
        every { concertRepository.findConcertSeat(concertSeatId) } returns null

        val concertService = ConcertService(concertRepository)

        // When, Then
        val exception = assertThrows<CustomException> {
            concertService.changeConcertSeatStatus(concertSeatId, concertId, SeatStatusType.AVAILABLE, SeatStatusType.BOOKED)
        }
        assert(exception.customError == ConcertError.CONCERT_SEAT_NOT_FOUND)
    }

    @Test
    fun `변경하려는 콘서트 좌석 상태가 AVAILABLE이 아니면 예외를 반환한다`() {
        // Given
        val concertId = 1L
        val concertSeatId = 1L
        val concertSeat = ConcertSeat(
            id = concertSeatId,
            concertId = concertId,
            seatNo = 1L,
            seatStatus = SeatStatusType.BOOKED,
            ticketPrice = 100L,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        val concertRepository = mockk<ConcertRepository>()
        every { concertRepository.findConcertSeat(concertSeatId) } returns concertSeat

        val concertService = ConcertService(concertRepository)

        // When, Then
        val exception = assertThrows<CustomException> {
            concertService.changeConcertSeatStatus(concertSeatId, concertId, SeatStatusType.AVAILABLE, SeatStatusType.BOOKED)
        }
        assert(exception.customError == ConcertError.SEAT_NOT_AVAILABLE)
    }
}
