package com.tikiticket.tickets.concert.domain.repository

import com.tikiticket.tickets.concert.domain.Concert
import java.time.LocalDate

interface ConcertRepository {
    fun findConcertsByDateRange(startDate: LocalDate, endDate: LocalDate): List<Concert>?
}