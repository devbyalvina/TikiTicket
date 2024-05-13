package com.tikiticket.tickets.aggregate.concert.application.eventlistener

import com.tikiticket.tickets.aggregate.concert.domain.ConcertService
import com.tikiticket.tickets.aggregate.concert.domain.SeatStatusType
import com.tikiticket.tickets.aggregate.payment.domain.event.PaymentCompletedChangeConcertEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

/**
 * 결제 완료 시 콘서트 변경 이벤트 리스터
 */
@Component
class ChangeConcertOnPaymentCompletedEventListener (
    private val concertService: ConcertService
){
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    operator fun invoke(event: PaymentCompletedChangeConcertEvent) {
        concertService.changeConcertSeatStatusWithPessimisticLock(event.concertSeatId, event.concertId, SeatStatusType.AVAILABLE, SeatStatusType.PAID)
    }
}