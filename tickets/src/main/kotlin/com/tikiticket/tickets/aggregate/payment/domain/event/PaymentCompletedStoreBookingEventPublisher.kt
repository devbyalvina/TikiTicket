package com.tikiticket.tickets.aggregate.payment.domain.event

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

/**
 * 결제 완료 시 예매 내역 저장 이벤트
 */
@Component
class PaymentCompletedStoreBookingEventPublisher (
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    operator fun invoke(event: PaymentCompletedStoreBookingEvent) {
        applicationEventPublisher.publishEvent(event)
    }
}