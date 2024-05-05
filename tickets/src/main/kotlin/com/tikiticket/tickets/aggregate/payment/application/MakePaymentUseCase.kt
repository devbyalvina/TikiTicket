package com.tikiticket.tickets.aggregate.payment.application

import com.tikiticket.tickets.aggregate.balance.domain.BalanceService
import com.tikiticket.tickets.aggregate.balance.domain.TransactionType
import com.tikiticket.tickets.aggregate.booking.domain.BookingService
import com.tikiticket.tickets.aggregate.booking.domain.BookingStatusType
import com.tikiticket.tickets.aggregate.concert.domain.ConcertService
import com.tikiticket.tickets.aggregate.concert.domain.SeatStatusType
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

/**
 *  API.8 예매 내역 결제
 */
@Component
class MakePaymentUseCase (
    private val bookingService: BookingService,
    private val concertService: ConcertService,
    private val balanceService: BalanceService,
    private val paymentService: com.tikiticket.tickets.aggregate.payment.domain.PaymentService,
    ) {
    @Transactional
    operator fun invoke(makePaymentCommand: com.tikiticket.tickets.aggregate.payment.application.MakePaymentCommand): com.tikiticket.tickets.aggregate.payment.domain.Payment {
        val currentDateTime = LocalDateTime.now()
        // 예매 상태 변경
        val paidBooking = bookingService.changeBookingStatus(makePaymentCommand.bookingId, BookingStatusType.PAID ,currentDateTime)

        // 콘서트 좌석 상태 변경
        concertService.changeConcertSeatStatusWithPessimisticLock(paidBooking.seatId, paidBooking.concertId, SeatStatusType.BOOKED, SeatStatusType.PAID)

        // 잔고 변경
        val changedBalance = balanceService.changeBalance(makePaymentCommand.payerId, paidBooking.ticketPrice, TransactionType.PAY, currentDateTime)

        // 결제
        val storedPayment = paymentService.makePayment(makePaymentCommand.bookingId, makePaymentCommand.paymentMethod, makePaymentCommand.payerId, paidBooking.ticketPrice, currentDateTime)
        return storedPayment
    }
}