package com.tikiticket.tickets.concert.application

import com.tikiticket.tickets.concert.application.exception.ConcertError
import com.tikiticket.tickets.concert.application.exception.ConcertException
import com.tikiticket.tickets.concert.domain.Concert
import com.tikiticket.tickets.concert.domain.ConcertService
import org.springframework.stereotype.Component

/**
 *  API.5 콘서트 좌석 목록 조회
 */
@Component
class GetConcertWithSeatsUseCase (
    private val concertService: ConcertService
){
    operator fun invoke(concertId: Long): Concert {
        val concert = concertService.findConcertWithSeats(concertId)
            ?: throw ConcertException(ConcertError.CONCERT_NOT_FOUND)

        ConcertValidator.checkSeatsExist(concert)

        return concert
    }
}