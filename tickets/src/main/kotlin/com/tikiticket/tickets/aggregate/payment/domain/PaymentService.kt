package com.tikiticket.tickets.aggregate.payment.domain

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class PaymentService (
    private val paymentRepository: com.tikiticket.tickets.aggregate.payment.domain.PaymentRepository,
){
    /**
     * 결제
     */
    fun storePayment(payment: com.tikiticket.tickets.aggregate.payment.domain.Payment): com.tikiticket.tickets.aggregate.payment.domain.Payment {
        return paymentRepository.savePayment(payment)
    }

    /**
     * 결제 내역 조회
     */
    fun findPayment(id: Long): com.tikiticket.tickets.aggregate.payment.domain.Payment? {
        return paymentRepository.findPaymentById(id)
    }

    /**
     * 결제
     */
    @Transactional
    fun makePayment(bookingId: Long, paymentMethod: com.tikiticket.tickets.aggregate.payment.domain.PaymentMethodType, payerId: String, paymentAmount: Long, currentDateTime: LocalDateTime): com.tikiticket.tickets.aggregate.payment.domain.Payment {
        // 결제 내역 저장
        val payment = com.tikiticket.tickets.aggregate.payment.domain.Payment(
            id = 0,
            bookingId = bookingId,
            paymentMethod = paymentMethod,
            paymentAmount = paymentAmount,
            payerId = payerId,
            paymentDateTime = currentDateTime,
            paymentStatus = com.tikiticket.tickets.aggregate.payment.domain.PaymentStatusType.SUCCESS,
            createdAt = currentDateTime,
            updatedAt = currentDateTime
        )
        val storedPayment = paymentRepository.savePayment(payment) // paymentRepository를 통해 결제 저장

        // 결제 히스토리 저장
        val paymentHistory = com.tikiticket.tickets.aggregate.payment.domain.PaymentHistory(
            paymentId = storedPayment.id,
            paymentHistoryId = 0,
            bookingId = storedPayment.bookingId,
            paymentMethod = storedPayment.paymentMethod,
            paymentAmount = paymentAmount,
            payerId = storedPayment.payerId,
            paymentDateTime = storedPayment.paymentDateTime,
            paymentStatus = storedPayment.paymentStatus,
            createdAt = storedPayment.createdAt
        )
        paymentRepository.savePaymentHistory(paymentHistory) // paymentRepository를 통해 결제 이력 저장

        return storedPayment
    }
}