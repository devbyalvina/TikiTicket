package com.tikiticket.tickets.aggregate.concert.infrastructure

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class ConcertSeatEntityPk(
    var concertId: Long,
    var seatNo: Long,
): Serializable
