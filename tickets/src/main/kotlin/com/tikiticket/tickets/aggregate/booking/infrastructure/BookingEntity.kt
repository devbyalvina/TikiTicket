package com.tikiticket.tickets.aggregate.booking.infrastructure

import com.tikiticket.tickets.global.infrastructure.jpa.BaseEntity
import com.tikiticket.tickets.aggregate.booking.domain.Booking
import com.tikiticket.tickets.aggregate.booking.domain.BookingStatusType
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
@Table(name = "booking")
class BookingEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val bookingId: Long,

    @NotNull
    val bookerId: String,

    @Enumerated(EnumType.STRING)
    @NotNull
    val bookingStatus: BookingStatusType,

    @NotNull
    val expiryDateTime: LocalDateTime,

    @NotNull
    val concertId: Long,

    @NotNull
    val concertName: String,

    @NotNull
    val artistName: String,

    @NotNull
    val concertDate: LocalDateTime,

    @NotNull
    val venue: String,

    @NotNull
    val seatId: Long,

    @NotNull
    val seatNo: Long,

    @NotNull
    val ticketPrice: Long,
): BaseEntity() {
    fun toDomain(): Booking {
        return Booking(
            id = this.bookingId ?: 0,
            bookerId = this.bookerId,
            bookingStatus = this.bookingStatus,
            expiryDateTime = this.expiryDateTime,
            concertId = this.concertId,
            concertName = this.concertName,
            artistName = this.artistName,
            concertDate = this.concertDate,
            venue = this.venue,
            seatId = this.seatId,
            seatNo = this.seatNo,
            ticketPrice = this.ticketPrice,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }

    companion object {
        fun of(booking: Booking): BookingEntity {
            return BookingEntity (
                bookingId = booking.id,
                bookerId = booking.bookerId,
                bookingStatus = booking.bookingStatus,
                expiryDateTime = booking.expiryDateTime,
                concertId = booking.concertId,
                concertName = booking.concertName,
                artistName = booking.artistName,
                concertDate = booking.concertDate,
                venue = booking.venue,
                seatId = booking.seatId,
                seatNo = booking.seatNo,
                ticketPrice = booking.ticketPrice,
            )
        }
    }
}