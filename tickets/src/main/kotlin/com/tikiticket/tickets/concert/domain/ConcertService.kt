package com.tikiticket.tickets.concert.domain

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ConcertService (
    private val concertRepository: ConcertRepository
){
    /**
     *  API.4. 콘서트 스케줄 목록 조회
     */
    fun getConcertsByDateRange(startDate: LocalDate, endDate: LocalDate): List<Concert>? {
        val concerts = concertRepository.findConcertsByDateRange(startDate, endDate)
        return concerts
    }

    /**
     *  API.5. 콘서트 좌석 목록 조회
     */
    fun getConcertWithSeatsById(concertId: Long): Concert? {
        val concert = concertRepository.findByIdWithSeats(concertId)
        return concert
    }
}