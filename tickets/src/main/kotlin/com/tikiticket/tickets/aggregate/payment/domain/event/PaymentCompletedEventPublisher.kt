package com.tikiticket.tickets.aggregate.payment.domain.event

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

/**
 * 결제 완료 이벤트 Publisher
 */
@Component
class PaymentCompletedEventPublisher (
    private val applicationEventPublisher: ApplicationEventPublisher,
){
    operator fun invoke(event: PaymentCompletedEvent) {
        applicationEventPublisher.publishEvent(event)
    }
}
