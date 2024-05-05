package com.tikiticket.tickets.aggregate.ticketqueuetoken

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.TestConstructor
import java.time.LocalDateTime
import java.util.concurrent.CompletableFuture
import java.util.concurrent.atomic.AtomicInteger

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class TicketQueueTokenIntegrationTest (
    val createTokenUseCase: com.tikiticket.tickets.aggregate.ticketqueuetoken.application.CreateTokenUseCase
){
    @Test
    fun `대기열 생성 요청이 동시에 들어오는 경우 1개만 성공한다`() {
        // when
        val failCount = AtomicInteger(0)
        val futures = mutableListOf<CompletableFuture<com.tikiticket.tickets.aggregate.ticketqueuetoken.domain.TicketQueueToken>>()
        repeat(10 ) {
            futures.add (
                CompletableFuture.supplyAsync {
                    runCatching {
                        createTokenUseCase ("user01", LocalDateTime.now())
                    }.onFailure {
                        failCount.incrementAndGet()
                    }.getOrNull()
                }
            )
        }

        CompletableFuture.allOf(*futures.toTypedArray()).join()

        Assertions.assertEquals(9, failCount.get())
    }
}