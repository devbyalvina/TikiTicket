package com.tikiticket.tickets.concert.infrastructure

import com.tikiticket.tickets.`app-core`.infrastructure.BaseEntity
import com.tikiticket.tickets.concert.domain.ConcertSeat
import com.tikiticket.tickets.concert.domain.SeatStatusType
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "concert_seat")
class ConcertSeatEntity(
    @EmbeddedId
    val concertSeatEntityPk: ConcertSeatEntityPk,

    @Enumerated(EnumType.STRING)
    @NotNull
    val seatStatus: SeatStatusType,

    @NotNull
    val ticketPrice: Long,
): BaseEntity() {
    fun toDomain(): ConcertSeat {
        return ConcertSeat(
            concertId = this.concertSeatEntityPk.concertId,
            seatNo = this.concertSeatEntityPk.seatNo,
            seatStatus = this.seatStatus,
            ticketPrice = this.ticketPrice,
            createdAt = this.createdAt!!,
            updatedAt = this.updatedAt!!
        )
    }
}