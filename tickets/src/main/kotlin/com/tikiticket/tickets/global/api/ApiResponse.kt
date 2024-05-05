package com.tikiticket.tickets.global.api

import com.tikiticket.tickets.global.domain.exception.CustomError

data class ApiResponse<T>(
var code: String,
val message: String,
val body: T? = null,
) {
    companion object {
        @JvmStatic
        fun <T> ok(): ApiResponse<T> {
            return process("SUCCESS", "success", null)
        }

        @JvmStatic
        fun <T> ok(body: T): ApiResponse<T> {
            return process("SUCCESS", "success", body)
        }

        @JvmStatic
        fun <T> error(error: CustomError): ApiResponse<T> {
            return process(error.errorCode, error.message, null)
        }

        @JvmStatic
        fun <T> error(error: CustomError, message: String): ApiResponse<T> {
            return process(error.errorCode, message, null)
        }

        @JvmStatic
        private fun <T> process(code: String, message: String, body: T?): ApiResponse<T> {
            return ApiResponse(code, message, body)
        }
    }
}