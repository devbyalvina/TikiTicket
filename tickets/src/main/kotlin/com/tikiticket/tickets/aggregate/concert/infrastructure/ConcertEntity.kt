package com.tikiticket.tickets.aggregate.concert.infrastructure

import com.tikiticket.tickets.aggregate.concert.domain.Concert
import com.tikiticket.tickets.aggregate.concert.domain.ConcertSeats
import com.tikiticket.tickets.global.infrastructure.jpa.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

@Entity
@Table(name = "concert")
class ConcertEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val concertId: Long,

    @NotNull
    val concertName: String,

    @NotNull
    val artistName: String,

    @NotNull
    val concertDate: LocalDateTime,

    @NotNull
    val venue: String,
): BaseEntity() {
    fun toDomain(): Concert {
        return Concert(
            id = this.concertId,
            concertName = this.concertName,
            artistName = this.artistName,
            concertDate = this.concertDate,
            venue = this.venue,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            seats = null
        )
    }

    fun toDomain(seats: List<ConcertSeatEntity>?): Concert {
        return Concert(
            id = this.concertId,
            concertName = this.concertName,
            artistName = this.artistName,
            concertDate = this.concertDate,
            venue = this.venue,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            seats = ConcertSeats(seats?.map { it.toDomain() })
        )
    }

    companion object {
        fun of(concert: Concert): ConcertEntity {
            return ConcertEntity (
                concertId = concert.id,
                concertName = concert.concertName,
                artistName = concert.artistName,
                concertDate = concert.concertDate,
                venue = concert.venue,
            )
        }
    }
}