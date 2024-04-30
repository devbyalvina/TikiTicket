package com.tikiticket.tickets.concert.application

import com.tikiticket.tickets.appcore.domain.exception.CustomException
import org.springframework.boot.logging.LogLevel
import com.tikiticket.tickets.concert.domain.ConcertError
import java.time.LocalDate

data class GetConcertsByDateRangeCommand (
    val startDate: LocalDate,
    val endDate: LocalDate
) {
    init {
        validate()
    }

    fun validate() {
        checkStartDateBeforeEndDate()
    }

    /**
     *  startDate가 endDate보다 이전인지 체크
     */
    fun checkStartDateBeforeEndDate() {
        if (startDate > endDate) {
            throw CustomException(LogLevel.WARN, ConcertError.INVALID_DATE_RANGE_PARAMETER)
        }
    }
}