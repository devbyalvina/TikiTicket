package com.tikiticket.tickets.concert.infrastructure

import com.tikiticket.tickets.appcore.infrastructure.BaseEntity
import com.tikiticket.tickets.concert.domain.ConcertSeat
import com.tikiticket.tickets.concert.domain.SeatStatusType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "concert_seat")
class ConcertSeatEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val concertSeatId: Long,

    @NotNull
    val concertId: Long,

    @NotNull
    val seatNo: Long,

    @Enumerated(EnumType.STRING)
    @NotNull
    val seatStatus: SeatStatusType,

    @NotNull
    val ticketPrice: Long,
): BaseEntity() {
    fun toDomain(): ConcertSeat {
        return ConcertSeat(
            id = this.concertSeatId,
            concertId = this.concertId,
            seatNo = this.seatNo,
            seatStatus = this.seatStatus,
            ticketPrice = this.ticketPrice,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }

    companion object {
        fun of(concertSeat: ConcertSeat): ConcertSeatEntity {
            return ConcertSeatEntity (
                concertSeatId = concertSeat.id,
                concertId = concertSeat.concertId,
                seatNo = concertSeat.seatNo,
                seatStatus = concertSeat.seatStatus,
                ticketPrice = concertSeat.ticketPrice
            )
        }
    }
}