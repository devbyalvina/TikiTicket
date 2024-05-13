package com.tikiticket.tickets.aggregate.payment.domain.event

import org.springframework.context.ApplicationEventPublisher

/**
 * 결제 완료 시 예매 내역 저장 이벤트
 */
class PaymentCompletedStoreBookingEventPublisher (
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    operator fun invoke(event: PaymentCompletedStoreBookingEvent) {
        applicationEventPublisher.publishEvent(event)
    }
}