package com.tikiticket.tickets.payment.infrastructure

import com.tikiticket.tickets.app_core.infrastructure.BaseEntity
import com.tikiticket.tickets.payment.domain.Payment
import com.tikiticket.tickets.payment.domain.PaymentMethodType
import com.tikiticket.tickets.payment.domain.PaymentStatus
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
@Table(name = "payment")
class PaymentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val paymentId: Long,

    @NotNull
    val bookingId: Long,

    @Enumerated(EnumType.STRING)
    @NotNull
    val paymentMethod: PaymentMethodType,

    @NotNull
    val paymentAmount: Long,

    @NotNull
    val payerId: String,

    @NotNull
    val paymentDateTime: LocalDateTime,

    @Enumerated(EnumType.STRING)
    @NotNull
    val paymentStatus: PaymentStatus,
): BaseEntity() {
    fun toDomain(): Payment {
        return Payment(
            id = this.paymentId,
            bookingId = this.bookingId,
            paymentMethod = this.paymentMethod,
            paymentAmount = this.paymentAmount,
            payerId = this.payerId,
            paymentDateTime = this.paymentDateTime,
            paymentStatus = this.paymentStatus,
            createdAt = this.createdAt!!,
            updatedAt = this.updatedAt!!
        )
    }
}