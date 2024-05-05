package com.tikiticket.tickets.aggregate.payment.infrastructure

import com.tikiticket.tickets.global.infrastructure.jpa.BaseEntity
import com.tikiticket.tickets.aggregate.payment.domain.PaymentHistory
import com.tikiticket.tickets.aggregate.payment.domain.PaymentMethodType
import com.tikiticket.tickets.aggregate.payment.domain.PaymentStatusType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

@Entity
@Table(name = "payment_history")
class PaymentHistoryEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val paymentHistoryId: Long,

    @NotNull
    val paymentId: Long,

    @NotNull
    val bookingId: Long,

    @Enumerated(EnumType.STRING)
    @NotNull
    val paymentMethod: com.tikiticket.tickets.aggregate.payment.domain.PaymentMethodType,

    @NotNull
    val paymentAmount: Long,

    @NotNull
    val payerId: String,

    @NotNull
    val paymentDateTime: LocalDateTime,

    @Enumerated(EnumType.STRING)
    @NotNull
    val paymentStatus: com.tikiticket.tickets.aggregate.payment.domain.PaymentStatusType,
): BaseEntity() {
    fun com.tikiticket.tickets.aggregate.payment.infrastructure.PaymentHistoryEntity.toDomain(): com.tikiticket.tickets.aggregate.payment.domain.PaymentHistory {
        return com.tikiticket.tickets.aggregate.payment.domain.PaymentHistory(
            paymentHistoryId = this.paymentHistoryId,
            paymentId = this.paymentId,
            bookingId = this.bookingId,
            paymentMethod = this.paymentMethod,
            paymentAmount = this.paymentAmount,
            payerId = this.payerId,
            paymentDateTime = this.paymentDateTime,
            paymentStatus = this.paymentStatus,
            createdAt = this.createdAt!!
        )
    }
}