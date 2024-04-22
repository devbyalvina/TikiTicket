package com.tikiticket.tickets.concert.infrastructure

import com.tikiticket.tickets.concert.domain.Concert
import com.tikiticket.tickets.concert.domain.ConcertRepository
import com.tikiticket.tickets.concert.domain.ConcertSeat
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class ConcertRepositoryImpl (
    private val concertJpaRepository: ConcertJpaRepository,
    private val concertSeatJpaRepository: ConcertSeatJpaRepository
): ConcertRepository {
    /**
     * 콘서트 조회
     */
    override fun findConcert(concertId: Long): Concert? {
        return concertJpaRepository.findByIdOrNull(concertId)?.toDomain()
    }

    override fun findConcertsByDateRange(startDate: LocalDate, endDate: LocalDate): List<Concert>? {
        TODO("Not yet implemented")
    }

    override fun findConcertSeats(concertId: Long): List<ConcertSeat>? {
        TODO("Not yet implemented")
    }

    override fun findConcertSeatForUpdate(concertId: Long, seatNo: Long): ConcertSeat? {
        TODO("Not yet implemented")
    }

    override fun updateConcertSeat(concertSeat: ConcertSeat) {
        TODO("Not yet implemented")
    }

}