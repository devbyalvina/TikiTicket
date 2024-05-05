package com.tikiticket.tickets.aggregate.concert.domain

import com.tikiticket.tickets.global.domain.exception.CustomException
import org.springframework.boot.logging.LogLevel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class ConcertService (
    private val concertRepository: ConcertRepository,
){
    /**
     * 콘서트 조회
     */
    fun findConcert(concertId: Long): Concert? {
        return concertRepository.findConcert(concertId)
    }

    /**
     *  콘서트 목록 조회
     */
    fun findConcertsByDateRange(startDate: LocalDate, endDate: LocalDate): List<Concert>? {
        return concertRepository.findConcertsByDateRange(startDate, endDate)
    }

    /**
     *  콘서트 좌석 목록 조회
     */
    fun findConcertWithSeats(concertId: Long): Concert? {
        val concert = concertRepository.findConcert(concertId)
        val concertSeats = concertRepository.findConcertSeats(concertId)

        return concert?.copy(seats = concertSeats?.map{it.copy()})
    }

    /**
     *  콘서트 좌석 수정
     */
    fun updateConcertSeat(concertSeat: ConcertSeat) {
        return concertRepository.updateConcertSeat(concertSeat)
    }

    /**
     *  콘서트 좌석 상태 변경 - 비관적락 사용
     */
    @Transactional
    fun changeConcertSeatStatusWithPessimisticLock(concertSeatId: Long, concertId: Long, previousStatus: SeatStatusType, targetStatus: SeatStatusType) {
        // 좌석 정보 조회 for update
        val concertSeat = concertRepository.findConcertSeatForUpdate(concertSeatId)
            ?: throw CustomException(LogLevel.INFO, ConcertError.CONCERT_SEAT_NOT_FOUND)

        // 좌석상태체크
        if (concertSeat.seatStatus != previousStatus) {
            throw CustomException(LogLevel.INFO, ConcertError.SEAT_NOT_AVAILABLE)
        }

        // 좌석 상태 변경
        concertRepository.storeConcertSeat(concertSeat.copy(seatStatus = targetStatus))
    }

    /**
     *  콘서트 좌석 상태 변경
     */
    fun changeConcertSeatStatus(concertSeatId: Long, concertId: Long, previousStatus: SeatStatusType, targetStatus: SeatStatusType) {
        // 좌석 정보 조회
        val concertSeat = concertRepository.findConcertSeat(concertSeatId)
            ?: throw CustomException(LogLevel.INFO, ConcertError.CONCERT_SEAT_NOT_FOUND)

        // 좌석상태체크
        if (concertSeat.seatStatus != previousStatus) {
            throw CustomException(LogLevel.INFO, ConcertError.SEAT_NOT_AVAILABLE)
        }

        // 좌석 상태 변경 후 저장
        concertRepository.storeConcertSeat(concertSeat.copy(seatStatus = targetStatus))
    }
}