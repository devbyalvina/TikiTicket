package com.tikiticket.tickets.aggregate.concert

import com.tikiticket.tickets.aggregate.concert.application.GetConcertWithSeatsUseCase
import com.tikiticket.tickets.global.infrastructure.jpa.JpaConfig
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.jdbc.Sql

@SpringBootTest
@Import(JpaConfig::class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Sql(scripts = [
    "classpath:data/concert.sql",
    "classpath:data/concert_seat.sql"
])
class ConcertIntegrationTest (
    val getConcertWithSeatsUseCase: GetConcertWithSeatsUseCase
){
    @Test
    fun `메모리 저장소에 대기열 토큰 생성 요청 시 토큰 생성에 성공한다`() {
        // When
        val result = getConcertWithSeatsUseCase(1)

        // Then
        Assertions.assertNotNull(result)
    }
}
