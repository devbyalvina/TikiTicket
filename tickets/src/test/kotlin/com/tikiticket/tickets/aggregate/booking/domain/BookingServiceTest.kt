package com.tikiticket.tickets.aggregate.booking.domain

import com.tikiticket.tickets.aggregate.booking.domain.Booking
import com.tikiticket.tickets.aggregate.booking.domain.BookingRepository
import com.tikiticket.tickets.aggregate.booking.domain.BookingService
import com.tikiticket.tickets.aggregate.booking.domain.BookingStatusType
import com.tikiticket.tickets.global.domain.exception.CustomException
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.time.LocalDateTime

class BookingServiceTest {
    // Mock Repository 생성
    private val bookingRepository: BookingRepository = mock(BookingRepository::class.java)

    // 테스트 대상 서비스
    private val bookingService = BookingService(bookingRepository)

    @Test
    fun `예매한다`() {
        // Given
        val booking = Booking(
            1L,
            bookerId = "user123",
            BookingStatusType.BOOKED,
            LocalDateTime.now().plusMinutes(5),
            123L,
            "Concert",
            "Artist",
            LocalDateTime.now(),
            "Venue",
            1L,
            1234L,
            10000L,
            LocalDateTime.now(),
            LocalDateTime.now()
        )

        // When
        `when`(bookingRepository.storeBooking(booking)).thenReturn(booking)

        val savedBooking = bookingService.makeBooking(booking)

        // Then
        assertEquals(booking, savedBooking)
    }

    @Test
    fun `예매 내역을 조회한다`() {
        // Given
        val bookingId = 1L
        val booking = Booking(
            bookingId,
            bookerId = "user123",
            BookingStatusType.BOOKED,
            LocalDateTime.now().plusMinutes(5),
            123L,
            "Concert",
            "Artist",
            LocalDateTime.now(),
            "Venue",
            1L,
            1234L,
            10000L,
            LocalDateTime.now(),
            LocalDateTime.now()
        )

        // When
        `when`(bookingRepository.findBookingById(bookingId)).thenReturn(booking)

        val foundBooking = bookingService.findBooking(bookingId)

        // Then
        assertEquals(booking, foundBooking)
    }

    @Test
    fun `예매 상태 변경에 성공한다`() {
        // Given
        val bookingId = 1L
        val currentDateTime = LocalDateTime.now()
        val bookingStatus = BookingStatusType.PAID

        val bookingRepository = mockk<BookingRepository>()
        val bookingService = BookingService(bookingRepository)

        val booking = Booking(
            id = bookingId,
            bookerId = "user123",
            bookingStatus = BookingStatusType.BOOKED,
            expiryDateTime = LocalDateTime.now().plusMinutes(30),
            concertId = 1L,
            concertName = "Concert",
            artistName = "Artist",
            concertDate = LocalDateTime.now().plusDays(7),
            venue = "Venue",
            seatId = 1L,
            seatNo = 1L,
            ticketPrice = 10000L,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        every { bookingRepository.findBookingByIdForUpdate(bookingId) } returns booking
        every { bookingRepository.changeBooking(any()) } just Runs

        // When
        val result = bookingService.changeBookingStatus(bookingId, bookingStatus, currentDateTime)

        // Then
        assertEquals(bookingId, result.id)
        assertEquals(bookingStatus, result.bookingStatus)
        verify(exactly = 1) { bookingRepository.findBookingByIdForUpdate(bookingId) }
        verify(exactly = 1) { bookingRepository.changeBooking(any()) }
    }

    @Test
    fun `예매 상태 변경 시 예약 내역이 없는 경우 예외를 봔환한다`() {
        // Given
        val bookingId = 1L
        val currentDateTime = LocalDateTime.now()
        val bookingStatus = BookingStatusType.PAID

        val bookingRepository = mockk<BookingRepository>()
        val bookingService = BookingService(bookingRepository)

        every { bookingRepository.findBookingByIdForUpdate(bookingId) } returns null

        // When & Then
        assertThrows(CustomException::class.java) {
            bookingService.changeBookingStatus(bookingId, bookingStatus, currentDateTime)
        }
    }
}