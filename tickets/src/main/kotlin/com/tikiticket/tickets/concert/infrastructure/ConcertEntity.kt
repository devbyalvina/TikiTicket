package com.tikiticket.tickets.concert.infrastructure

import com.tikiticket.tickets.`app-core`.infrastructure.BaseEntity
import com.tikiticket.tickets.concert.domain.Concert
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
    val id: Long,

    @NotNull
    val concertName: String,

    @NotNull
    val artistName: String,

    @NotNull
    val concertDate: LocalDateTime,

    @NotNull
    val venue: String,
): BaseEntity() {
    fun toDomain(seats: List<ConcertSeatEntity>?): Concert {
        return Concert(
            id = this.id ?: 0,
            concertName = this.concertName,
            artistName = this.artistName,
            concertDate = this.concertDate,
            venue = this.venue,
            createdAt = this.createdAt!!,
            updatedAt = this.updatedAt!!,
            seats = seats?.map { it.toDomain() }
        )
    }
}