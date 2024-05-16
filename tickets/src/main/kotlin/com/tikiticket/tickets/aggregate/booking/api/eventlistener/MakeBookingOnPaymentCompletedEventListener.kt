package com.tikiticket.tickets.aggregate.booking.api.eventlistener

import com.tikiticket.tickets.aggregate.booking.application.MakeBookingInMemoryUseCase
import com.tikiticket.tickets.aggregate.payment.domain.event.PaymentCompletedEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

/**
 * 결제 완료 시 예매 내역 저장 이벤트 리스너
 */
class MakeBookingOnPaymentCompletedEventListener (
    private val makeBookingInMemoryUseCase: MakeBookingInMemoryUseCase
) {
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    operator fun invoke(event: PaymentCompletedEvent) {
        // 임시 예약 내용을 저장소에 저장하는 서비스 로직
    }
}