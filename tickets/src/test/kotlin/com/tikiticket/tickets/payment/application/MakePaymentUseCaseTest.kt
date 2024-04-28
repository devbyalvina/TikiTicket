package com.tikiticket.tickets.payment.application

import com.tikiticket.tickets.balance.domain.Balance
import com.tikiticket.tickets.balance.domain.BalanceService
import com.tikiticket.tickets.balance.domain.TransactionType
import com.tikiticket.tickets.booking.domain.Booking
import com.tikiticket.tickets.booking.domain.BookingService
import com.tikiticket.tickets.booking.domain.BookingStatusType
import com.tikiticket.tickets.payment.domain.Payment
import com.tikiticket.tickets.payment.domain.PaymentMethodType
import com.tikiticket.tickets.payment.domain.PaymentService
import com.tikiticket.tickets.payment.domain.PaymentStatus
import io.mockk.every
import io.mockk.mockk
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
        val paymentMethod = "CARD"
        val currentDateTime = LocalDateTime.now()
        val makePaymentCommand = MakePaymentCommand(bookingId, paymentMethod, payerId)

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
            seatNo = 1L,
            ticketPrice = 100L,
            createdAt = currentDateTime,
            updatedAt = currentDateTime
        )
        every { bookingService.changeBookingStatus(bookingId, BookingStatusType.PAID, any()) } returns paidBooking

        val balanceService = mockk<BalanceService>()
        val changedBalance = Balance(payerId, 400L, currentDateTime, currentDateTime)
        every { balanceService.changeBalance(payerId, paidBooking.ticketPrice, TransactionType.PAY, any()) } returns changedBalance

        val paymentService = mockk<PaymentService>()
        val storedPayment = Payment(
            id = 1L,
            bookingId = bookingId,
            paymentMethod = PaymentMethodType.BALANCE,
            paymentAmount = paidBooking.ticketPrice,
            payerId = payerId,
            paymentDateTime = currentDateTime,
            paymentStatus = PaymentStatus.SUCCESS,
            createdAt = currentDateTime,
            updatedAt = currentDateTime
        )
        every { paymentService.makePayment(bookingId, PaymentMethodType.BALANCE, payerId, paidBooking.ticketPrice, any()) } returns storedPayment

        val makePaymentUseCase = MakePaymentUseCase(bookingService, balanceService, paymentService)

        // When
        val result = makePaymentUseCase(makePaymentCommand)

        // Then
        verify(exactly = 1) { bookingService.changeBookingStatus(bookingId, BookingStatusType.PAID, any()) }
        verify(exactly = 1) { balanceService.changeBalance(payerId, paidBooking.ticketPrice, TransactionType.PAY, any()) }
        verify(exactly = 1) { paymentService.makePayment(bookingId, PaymentMethodType.BALANCE, payerId, paidBooking.ticketPrice, any()) }

        Assertions.assertEquals(storedPayment, result)
    }
}