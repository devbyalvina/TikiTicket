package com.tikiticket.tickets.concert.domain

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ConcertService (
    private val concertRepository: ConcertRepository
){
    /**
     * 콘서트 조회
     */
    fun findConcert(concertId: Long): Concert? {
        return concertRepository.findConcertById(concertId)
    }

    /**
     *  콘서트 스케줄 목록 조회
     */
    fun findConcertsByDateRange(startDate: LocalDate, endDate: LocalDate): List<Concert>? {
        return concertRepository.findConcertsByDateRange(startDate, endDate)
    }

    /**
     *  콘서트 좌석 목록 조회
     */
    fun findConcertWithSeatsById(concertId: Long): Concert? {
        return concertRepository.findConcertWithSeatsById(concertId)
    }

    /**
     *  콘서트 좌석 조회
     */
    fun findConcertSeatByConcertIdAndSeatNo(ConcertId: Long, SeatNo: Long): ConcertSeat? {
        return concertRepository.findConcertSeatByConcertIdAndSeatNo(ConcertId, SeatNo)
    }

    /**
     *  콘서트 좌석 조회 For Update
     */
    fun findConcertSeatForUpdate(ConcertId: Long, SeatNo: Long): ConcertSeat? {
        return concertRepository.findConcertSeatForUpdate(ConcertId, SeatNo)
    }

    /**
     *  콘서트 좌석 수정
     */
    fun updateConcertSeat(concertSeat: ConcertSeat) {
        return concertRepository.updateConcertSeat(concertSeat)
    }
}