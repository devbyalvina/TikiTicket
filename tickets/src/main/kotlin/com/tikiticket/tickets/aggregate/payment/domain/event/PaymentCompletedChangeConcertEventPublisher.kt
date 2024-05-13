package com.tikiticket.tickets.aggregate.payment.domain.event

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

/**
 * 결제 완료 시 콘서트 좌석 변경 이벤트
 */
@Component
class PaymentCompletedChangeConcertEventPublisher (
    private val applicationEventPublisher: ApplicationEventPublisher,
){
    operator fun invoke(event: PaymentCompletedChangeConcertEvent) {
        applicationEventPublisher.publishEvent(event)
    }
}