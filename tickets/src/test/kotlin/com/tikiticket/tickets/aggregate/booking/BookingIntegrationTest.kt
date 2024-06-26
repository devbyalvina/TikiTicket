package com.tikiticket.tickets.aggregate.booking

import com.tikiticket.tickets.aggregate.booking.application.MakeBookingByRedissonLockUseCase
import com.tikiticket.tickets.aggregate.booking.application.MakeBookingCommand
import com.tikiticket.tickets.aggregate.booking.application.MakeBookingUseCase
import com.tikiticket.tickets.aggregate.booking.application.MakeTemporaryBookingUseCase
import com.tikiticket.tickets.aggregate.booking.domain.Booking
import com.tikiticket.tickets.global.infrastructure.jpa.JpaConfig
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.jdbc.Sql
import java.util.concurrent.CompletableFuture
import java.util.concurrent.atomic.AtomicInteger

@SpringBootTest
@Import(JpaConfig::class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Sql(scripts = [
    "classpath:data/concert.sql",
    "classpath:data/concert_seat.sql"
])
class BookingIntegrationTest(
    val makeBookingUseCase: MakeBookingUseCase,
    val makeBookingByRedissonLockUseCase: MakeBookingByRedissonLockUseCase,
    val makeTemporaryBookingUseCase: MakeTemporaryBookingUseCase,
) {
    @Test
    fun `좌석 예약 요청이 동시에 들어오는 경우 1개만 성공한다`() {
        // when
        val failCount = AtomicInteger(0)
        val futures = mutableListOf<CompletableFuture<Booking>>()
        repeat(10 ) {
            futures.add (
                CompletableFuture.supplyAsync {
                    runCatching {
                        makeBookingUseCase (MakeBookingCommand("user01", 1, 1))
                    }.onFailure {
                        failCount.incrementAndGet()
                    }.getOrNull()
                }
            )
        }

        CompletableFuture.allOf(*futures.toTypedArray()).join()

        Assertions.assertEquals(9, failCount.get())
    }

    @Test
    fun `좌석 예약 요청이 동시에 들어오는 경우 1개만 성공한다 - Redisson`() {
        // when
        val failCount = AtomicInteger(0)
        val futures = mutableListOf<CompletableFuture<Booking>>()
        repeat(10 ) {
            futures.add (
                CompletableFuture.supplyAsync {
                    runCatching {
                        makeBookingByRedissonLockUseCase (MakeBookingCommand("user01", 1, 1))
                    }.onFailure {
                        failCount.incrementAndGet()
                    }.getOrNull()
                }
            )
        }

        CompletableFuture.allOf(*futures.toTypedArray()).join()

        Assertions.assertEquals(9, failCount.get())
    }

    @Test
    fun `임시 예약 요청이 동시에 들어오는 경우 1개만 성공한다 - Redisson`() {
        // when
        val failCount = AtomicInteger(0)
        val futures = mutableListOf<CompletableFuture<Booking>>()
        repeat(100 ) {
            futures.add (
                CompletableFuture.supplyAsync {
                    runCatching {
                        makeTemporaryBookingUseCase (MakeBookingCommand("user01", 1, 1))
                    }.onFailure {
                        failCount.incrementAndGet()
                    }.getOrNull()
                }
            )
        }

        CompletableFuture.allOf(*futures.toTypedArray()).join()

        Assertions.assertEquals(99, failCount.get())
    }
}




