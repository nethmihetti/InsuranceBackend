package com.inno.soramitsu.insurance.RESTserver.util.exception

import org.springframework.http.HttpStatus

/**
 * Created by nethmih on 14.04.19.
 */

enum class ServerErrorCodes private constructor(val desc: String, val status: HttpStatus) {

    SERVICE_FAILURE("internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    TYPE_INVALID("bad request", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST("not found", HttpStatus.NOT_FOUND),
    FORBIDDEN("forbidden", HttpStatus.FORBIDDEN),
    CONFLICT("conflict", HttpStatus.CONFLICT)
}