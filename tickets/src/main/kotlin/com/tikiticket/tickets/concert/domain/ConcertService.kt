package com.tikiticket.tickets.concert.domain

import com.tikiticket.tickets.concert.domain.exception.ConcertError
import com.tikiticket.tickets.concert.domain.exception.ConcertException
import com.tikiticket.tickets.concert.domain.repository.ConcertRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ConcertService (
    private val concertRepository: ConcertRepository
){
    fun getConcertsByDateRange(startDate: LocalDate, endDate: LocalDate): List<Concert> {
        val concerts = concertRepository.findConcertsByDateRange(startDate, endDate) ?: throw ConcertException(ConcertError.CONCERT_NOT_FOUND)
        return concerts
    }
}