package com.tikiticket.tickets.aggregate.payment.application

import com.tikiticket.tickets.aggregate.balance.domain.BalanceService
import com.tikiticket.tickets.aggregate.balance.domain.TransactionType
import com.tikiticket.tickets.aggregate.payment.domain.Payment
import com.tikiticket.tickets.aggregate.payment.domain.PaymentService
import com.tikiticket.tickets.aggregate.payment.domain.event.PaymentCompletedEvent
import com.tikiticket.tickets.aggregate.payment.domain.event.PaymentCompletedEventPublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

/**
 *  API.8 예매 내역 결제 - 결제 완료 이벤트 발행
 */
@Component
class MakePaymentWithEventUseCase (
    private val balanceService: BalanceService,
    private val paymentService: PaymentService,
    private val paymentCompletedEventPublisher: PaymentCompletedEventPublisher
) {
    @Transactional
    operator fun invoke(command: MakePaymentWithEventCommand): Payment {
        val currentDateTime = LocalDateTime.now()
        // 잔고 변경
        balanceService.changeBalance(command.payerId, command.ticketPrice, TransactionType.PAY, currentDateTime)

        // 결제
        val storedPayment = paymentService.makePayment(command.paymentMethod, command.payerId, command.ticketPrice, currentDateTime)

        // 결제 완료 이벤트
        val paymentCompletedEvent = PaymentCompletedEvent(storedPayment.id, command.concertId, command.concertSeatId, command.payerId)
        paymentCompletedEventPublisher.invoke(paymentCompletedEvent)

        return storedPayment
    }
}