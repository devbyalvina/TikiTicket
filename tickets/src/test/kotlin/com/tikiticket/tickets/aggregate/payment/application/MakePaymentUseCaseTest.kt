package com.tikiticket.tickets.aggregate.payment.application

import com.tikiticket.tickets.aggregate.balance.domain.Balance
import com.tikiticket.tickets.aggregate.balance.domain.BalanceService
import com.tikiticket.tickets.aggregate.balance.domain.TransactionType
import com.tikiticket.tickets.aggregate.booking.domain.Booking
import com.tikiticket.tickets.aggregate.booking.domain.BookingService
import com.tikiticket.tickets.aggregate.booking.domain.BookingStatusType
import com.tikiticket.tickets.aggregate.concert.domain.ConcertService
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class MakePaymentUseCaseTest {
    @Test
    fun `결제에 성공한다`() {
        // Given
        val bookingId = 123L
        val payerId = "user123"
        val paymentMethod = com.tikiticket.tickets.aggregate.payment.domain.PaymentMethodType.BALANCE
        val currentDateTime = LocalDateTime.now()
        val makePaymentCommand =
            MakePaymentCommand(bookingId, paymentMethod, payerId)

        // bookingService mocking
        val bookingService = mockk<BookingService>()
        val paidBooking = Booking(
            id = bookingId,
            bookerId = payerId,
            bookingStatus = BookingStatusType.PAID,
            expiryDateTime = currentDateTime,
            concertId = 1L,
            concertName = "Concert",
            artistName = "Artist",
            concertDate = LocalDateTime.now(),
            venue = "Venue",
            seatId = 1L,
            seatNo = 1L,
            ticketPrice = 100L,
            createdAt = currentDateTime,
            updatedAt = currentDateTime
        )
        every { bookingService.changeBookingStatus(bookingId, BookingStatusType.PAID, any()) } returns paidBooking

        // concertService mocking
        val concertService = mockk<ConcertService>()
        every { concertService.changeConcertSeatStatusWithPessimisticLock(paidBooking.seatId, paidBooking.concertId, any(), any()) } just runs

        // balanceService mocking
        val balanceService = mockk<BalanceService>()
        val changedBalance = Balance(1L, payerId, 400L, currentDateTime, currentDateTime)
        every { balanceService.changeBalance(payerId, paidBooking.ticketPrice, TransactionType.PAY, any()) } returns changedBalance

        // paymentService mocking
        val paymentService = mockk<com.tikiticket.tickets.aggregate.payment.domain.PaymentService>()
        val storedPayment = com.tikiticket.tickets.aggregate.payment.domain.Payment(
            id = 1L,
            bookingId = bookingId,
            paymentMethod = com.tikiticket.tickets.aggregate.payment.domain.PaymentMethodType.BALANCE,
            paymentAmount = paidBooking.ticketPrice,
            payerId = payerId,
            paymentDateTime = currentDateTime,
            paymentStatus = com.tikiticket.tickets.aggregate.payment.domain.PaymentStatusType.SUCCESS,
            createdAt = currentDateTime,
            updatedAt = currentDateTime
        )
        every { paymentService.makePayment(bookingId, com.tikiticket.tickets.aggregate.payment.domain.PaymentMethodType.BALANCE, payerId, paidBooking.ticketPrice, any()) } returns storedPayment

        val makePaymentUseCase = MakePaymentUseCase(
            bookingService,
            concertService,
            balanceService,
            paymentService
        )

        // When
        val result = makePaymentUseCase(makePaymentCommand)

        // Then
        verify(exactly = 1) { bookingService.changeBookingStatus(bookingId, BookingStatusType.PAID, any()) }
        verify(exactly = 1) { concertService.changeConcertSeatStatusWithPessimisticLock(paidBooking.seatId, paidBooking.concertId, any(), any()) }
        verify(exactly = 1) { balanceService.changeBalance(payerId, paidBooking.ticketPrice, TransactionType.PAY, any()) }
        verify(exactly = 1) { paymentService.makePayment(bookingId, com.tikiticket.tickets.aggregate.payment.domain.PaymentMethodType.BALANCE, payerId, paidBooking.ticketPrice, any()) }

        Assertions.assertEquals(storedPayment, result)
    }
}