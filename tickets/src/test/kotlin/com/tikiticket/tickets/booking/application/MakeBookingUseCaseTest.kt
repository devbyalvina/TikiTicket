package com.tikiticket.tickets.booking.application

import com.tikiticket.tickets.appcore.domain.exception.CustomException
import com.tikiticket.tickets.booking.domain.Booking
import com.tikiticket.tickets.booking.domain.BookingError
import com.tikiticket.tickets.booking.domain.BookingService
import com.tikiticket.tickets.booking.domain.BookingStatusType
import com.tikiticket.tickets.concert.domain.Concert
import com.tikiticket.tickets.concert.domain.ConcertSeat
import com.tikiticket.tickets.concert.domain.ConcertService
import com.tikiticket.tickets.concert.domain.SeatStatusType
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class MakeBookingUseCaseTest {
    @Test
    fun `예매에 성공한다`() {
        // Given
        val userId = "user01"
        val seatId = 1L
        val concertId = 1L
        val seatNo = 1L

        val concert = Concert(
            id = 1L,
            concertName = "Concert",
            artistName = "Artist",
            concertDate = LocalDateTime.now(),
            venue = "Venue",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            seats = listOf(
                ConcertSeat(
                    id = 1L,
                    concertId = 1L,
                    seatNo = 1L,
                    seatStatus = SeatStatusType.AVAILABLE,
                    ticketPrice = 100L,
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                )
            )
        )

        val expectedBooking = Booking(
            1,
            bookerId = "user123",
            BookingStatusType.BOOKED,
            LocalDateTime.now().plusMinutes(5),
            concertId, "Concert",
            "Artist",
            LocalDateTime.now(),
            "Venue",
            seatId, seatNo,
            10000L,
            LocalDateTime.now(),
            LocalDateTime.now()
        )

        val concertService = mockk<ConcertService>()
        every { concertService.changeConcertSeatStatus(any(), any(), any(), any()) } just Runs
        every { concertService.findConcertWithSeats(any()) } returns concert

        val bookingService = mockk<BookingService>()
        every { bookingService.makeBooking(any()) } returns expectedBooking

        val makeBookingUseCase = MakeBookingUseCase(bookingService, concertService)
        val command = MakeBookingCommand(userId, seatId, concertId)

        // When
        val result = makeBookingUseCase(command)

        // Then
        verify(exactly = 1) {
            concertService.changeConcertSeatStatus(command.concertSeatId, command.concertId, SeatStatusType.AVAILABLE, SeatStatusType.BOOKED)
            concertService.findConcertWithSeats(command.concertId)
            bookingService.makeBooking(any())
        }
        assert(result == expectedBooking)
    }

    @Test
    fun `콘서트가 없는 경우 예외를 반환한다`() {
        // Given
        val userId = "user01"
        val concertId = 1L
        val seatId = 1L
        val bookingService = mockk<BookingService>()
        val concertService = mockk<ConcertService> {
            every { changeConcertSeatStatus(any(), any(), any(), any()) } just Runs
            every { findConcertWithSeats(any()) } returns null
        }
        val makeBookingUseCase = MakeBookingUseCase(bookingService, concertService)
        val command = MakeBookingCommand(userId, seatId, concertId)

        // When & Then
        val exception = org.junit.jupiter.api.assertThrows<CustomException> {
            makeBookingUseCase(command)
        }
        assert(exception.customError == BookingError.CONCERT_NOT_FOUND)
    }

    @Test
    fun `예매된 좌석이 없는 경우 예외를 반환한다`() {
        // Given
        val userId = "user01"
        val seatId = 2L
        val concertId = 1L

        val concert = Concert(
            id = 1L,
            concertName = "Concert",
            artistName = "Artist",
            concertDate = LocalDateTime.now(),
            venue = "Venue",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            seats = listOf(
                ConcertSeat(
                    id = 1L,
                    concertId = 1L,
                    seatNo = 1L,
                    seatStatus = SeatStatusType.AVAILABLE,
                    ticketPrice = 100L,
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                )
            )
        )

        val bookingService = mockk<BookingService>()
        val concertService = mockk<ConcertService> {
            every { changeConcertSeatStatus(any(), any(), any(), any()) } just Runs
            every { findConcertWithSeats(any()) } returns concert
        }

        val makeBookingUseCase = MakeBookingUseCase(bookingService, concertService)
        val command = MakeBookingCommand(userId, seatId, concertId)

        // When & Then
        val exception = org.junit.jupiter.api.assertThrows<CustomException> {
            makeBookingUseCase(command)
        }
        assert(exception.customError == BookingError.CONCERT_SEAT_NOT_FOUND)
    }
}