package com.tikiticket.tickets.aggregate.booking.application

import com.tikiticket.tickets.aggregate.booking.domain.Booking
import com.tikiticket.tickets.aggregate.booking.domain.BookingError
import com.tikiticket.tickets.aggregate.booking.domain.BookingService
import com.tikiticket.tickets.aggregate.booking.domain.BookingStatusType
import com.tikiticket.tickets.aggregate.concert.domain.Concert
import com.tikiticket.tickets.aggregate.concert.domain.ConcertSeat
import com.tikiticket.tickets.aggregate.concert.domain.ConcertSeats
import com.tikiticket.tickets.aggregate.concert.domain.ConcertService
import com.tikiticket.tickets.aggregate.concert.domain.SeatStatusType
import com.tikiticket.tickets.global.domain.exception.CustomException
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
            seats = ConcertSeats(listOf(
                    ConcertSeat(
                        id = 1L,
                        concertId = 1L,
                        seatNo = 1L,
                        seatStatus = SeatStatusType.BOOKED,
                        ticketPrice = 100L,
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now()
                    )
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
        every { concertService.changeConcertSeatStatusWithPessimisticLock(any(), any(), any(), any()) } just Runs
        every { concertService.findConcertInMemory(any()) } returns concert

        val bookingService = mockk<BookingService>()
        every { bookingService.makeBooking(any()) } returns expectedBooking

        val makeBookingUseCase = MakeBookingUseCase(bookingService, concertService)
        val command = MakeBookingCommand(userId, seatId, concertId)

        // When
        val result = makeBookingUseCase(command)

        // Then
        verify(exactly = 1) {
            concertService.changeConcertSeatStatusWithPessimisticLock(command.concertSeatId, command.concertId, SeatStatusType.AVAILABLE, SeatStatusType.BOOKED)
            concertService.findConcertInMemory(command.concertId)
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
            every { changeConcertSeatStatusWithPessimisticLock(any(), any(), any(), any()) } just Runs
            every { findConcertInMemory(any()) } returns null
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
            seats = ConcertSeats(listOf(
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
        )

        val bookingService = mockk<BookingService>()
        val concertService = mockk<ConcertService> {
            every { changeConcertSeatStatusWithPessimisticLock(any(), any(), any(), any()) } just Runs
            every { findConcertInMemory(any()) } returns concert
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