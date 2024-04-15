package com.tikiticket.tickets.payment.application

import com.tikiticket.tickets.balance.domain.Balance
import com.tikiticket.tickets.balance.domain.BalanceService
import com.tikiticket.tickets.balance.domain.TransactionType
import com.tikiticket.tickets.booking.domain.BookingService
import com.tikiticket.tickets.booking.domain.BookingStatusType
import com.tikiticket.tickets.payment.domain.Payment
import com.tikiticket.tickets.payment.domain.PaymentService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

/**
 *  API.8 예매 내역 결제
 */
@Component
class MakePaymentUseCase (
    private val bookingService: BookingService,
    private val balanceService: BalanceService,
    private val paymentService: PaymentService,
    ) {
    @Transactional
    operator fun invoke(makePaymentCommand: MakePaymentCommand): Payment {
        val currentDateTime = LocalDateTime.now()
        // 예매 상태 변경
        val paidBooking = bookingService.changeBookingStatus(makePaymentCommand.bookingId, BookingStatusType.PAID ,currentDateTime)

        // 잔고 조회
        val existingBalance = balanceService.retrieveBalance(makePaymentCommand.payerId) ?: Balance(makePaymentCommand.payerId, 0, currentDateTime, currentDateTime)
        // 변경 금액 계산
        val calculatedAmount = existingBalance.calculateChangedBalance(TransactionType.PAY, paidBooking.ticketPrice)
        // 변경 금액 검증
        MakePaymentValidator.checkCalculatedAmount(calculatedAmount)
        // 잔고 변경
        val changedBalance = balanceService.changeBalance(existingBalance, TransactionType.PAY, calculatedAmount, currentDateTime)

        // 결제
        val paymentMethod = makePaymentCommand.getPaymentMethodTypeFromString()
        val storedPayment = paymentService.makePayment(makePaymentCommand.bookingId, paymentMethod, makePaymentCommand.payerId, paidBooking.ticketPrice, currentDateTime)
        return storedPayment
    }
}