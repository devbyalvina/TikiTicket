package com.tikiticket.tickets.appcore.api

import com.tikiticket.tickets.appcore.domain.exception.CustomException
import org.springframework.boot.logging.LogLevel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiControllerAdvice {
    val logger: Logger get() = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(
        CustomException::class
    )
    fun handleCustomException(e: CustomException): ApiResponse<Any> {
        when (e.logLevel) {
            LogLevel.ERROR -> {
                logger.error("ApiException : {}", e.message, e)
            }
            LogLevel.WARN -> {
                logger.warn("ApiException : {}", e.message, e)
            }
            else -> {
                logger.info("ApiException : {}", e.message, e)
            }
        }
        return ApiResponse.ok(e.customError)
    }

    @ExceptionHandler(
        Exception::class
    )
    fun handleException(e: Exception): ResponseEntity<String> {
        logger.error("Exception : {}", e.message, e)
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(e.message)
    }
}