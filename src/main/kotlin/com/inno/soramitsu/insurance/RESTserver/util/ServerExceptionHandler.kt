package com.inno.soramitsu.insurance.RESTserver.util

import com.inno.soramitsu.insurance.RESTserver.model.envelope.EnvelopedResponse
import com.inno.soramitsu.insurance.RESTserver.util.exception.ServerErrorCodes
import com.inno.soramitsu.insurance.RESTserver.util.exception.ServerErrorMessages
import com.inno.soramitsu.insurance.RESTserver.util.exception.ServerExceptionTO
import com.inno.soramitsu.insurance.RESTserver.util.exception.ServerExceptions
import org.springframework.beans.TypeMismatchException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.slf4j.LoggerFactory
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.web.bind.annotation.ResponseBody

/**
 * Created by nethmih on 14.04.19.
 */

@ControllerAdvice(basePackages = ["com.inno.soramitsu.insurance.RESTserver.controller"])
class ServerExceptionHandler: ResponseEntityExceptionHandler() {

    private val LOG = LoggerFactory.getLogger(ServerExceptionHandler::class.java)

    private val logPattern = "Response for the exception {} : {} "

    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleException(e: Exception): ResponseEntity<EnvelopedResponse<Any>> {

        LOG.error(e.message, e)
        val envelopedResponse: EnvelopedResponse<Any> = EnvelopedResponse()

        val exception = ServerExceptionTO(ServerErrorMessages.INTERNAL_ERROR, ServerErrorCodes.SERVICE_FAILURE.desc, e.message)

        envelopedResponse.error =listOf(exception)

        val responseEntity: ResponseEntity<EnvelopedResponse<Any>> = ResponseEntity(envelopedResponse,
                ServerErrorCodes.SERVICE_FAILURE.status)

        LOG.info(logPattern, e.message, responseEntity.toString())


        return responseEntity

    }

    @ExceptionHandler(ServerExceptions::class)
    @ResponseBody
    fun handleException(e: ServerExceptions): ResponseEntity<EnvelopedResponse<Any>> {

        LOG.error(e.message, e)

        val envelopedResponse: EnvelopedResponse<Any> = EnvelopedResponse()

        val exception = ServerExceptionTO(e.message, e.errorCode.desc, e.additionalInfo)

        envelopedResponse.error = listOf(exception)

        val responseEntity: ResponseEntity<EnvelopedResponse<Any>> = ResponseEntity(envelopedResponse, e.errorCode.status)

        LOG.info(logPattern, e.message, responseEntity.toString())

        return responseEntity

    }


    override fun handleMissingServletRequestParameter(ex: MissingServletRequestParameterException, headers: HttpHeaders,
                                                      status: HttpStatus, request: WebRequest): ResponseEntity<Any> {

        LOG.error(ex.message, ex)

        val envelopedResponse: EnvelopedResponse<Any> = EnvelopedResponse()
        val exception = ServerExceptionTO(ex.message, ServerErrorMessages.REQUIRED_PARAM_ERROR, ServerErrorCodes.TYPE_INVALID.desc)


        envelopedResponse.error = listOf(exception)

        val responseEntity: ResponseEntity<Any> = ResponseEntity(envelopedResponse, ServerErrorCodes.TYPE_INVALID.status)

        LOG.info(logPattern, ex.message, responseEntity.toString())

        return responseEntity

    }


    override fun handleTypeMismatch(ex: TypeMismatchException, headers: HttpHeaders,
                                    status: HttpStatus, request: WebRequest): ResponseEntity<Any> {

        LOG.error(ex.message, ex)

        val envelopedResponse: EnvelopedResponse<Any> = EnvelopedResponse()
        val exception = ServerExceptionTO(ServerErrorMessages.TYPE_ERROR, ServerErrorCodes.TYPE_INVALID.desc)


        envelopedResponse.error = listOf(exception)

        val responseEntity: ResponseEntity<Any> = ResponseEntity(envelopedResponse, ServerErrorCodes.TYPE_INVALID.status)

        LOG.info(logPattern, ex.message, responseEntity.toString())

        return responseEntity

    }


    override fun handleNoHandlerFoundException(
            ex: NoHandlerFoundException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {

        LOG.error(ex.message, ex)

        val envelopedResponse: EnvelopedResponse<Any> = EnvelopedResponse()
        val exception = ServerExceptionTO(ServerErrorMessages.NO_RESOURCE, ServerErrorCodes.INVALID_REQUEST.desc)


        envelopedResponse.error = listOf(exception)

        val responseEntity: ResponseEntity<Any> = ResponseEntity(envelopedResponse, ServerErrorCodes.INVALID_REQUEST.status)

        LOG.info(logPattern, ex.message, responseEntity.toString())

        return responseEntity

    }

    @ExceptionHandler(value=[DataIntegrityViolationException::class])
    @ResponseBody
    fun handleSQLException(e: Exception): ResponseEntity<EnvelopedResponse<Any>> {

        LOG.error(e.message, e)

        val envelopedResponse: EnvelopedResponse<Any> = EnvelopedResponse()
        val exception = ServerExceptionTO(e.message, ServerErrorMessages.DUPLICATE_VALUE, ServerErrorCodes.CONFLICT.desc)

        envelopedResponse.error = listOf(exception)

        val responseEntity =  ResponseEntity(envelopedResponse, ServerErrorCodes.CONFLICT.status)

        LOG.info(logPattern, e.message, responseEntity.toString())

        return responseEntity

    }

    @ExceptionHandler(value=[EmptyResultDataAccessException::class])
    @ResponseBody
    fun handleSQLExceptionEmptyResults(e: Exception): ResponseEntity<EnvelopedResponse<Any>> {

        LOG.error(e.message, e)

        val envelopedResponse: EnvelopedResponse<Any> = EnvelopedResponse()
        val exception = ServerExceptionTO(e.message, ServerErrorMessages.NULL_RESPONSE_FROM_DATABASE, ServerErrorCodes.INVALID_REQUEST.desc)

        envelopedResponse.error = listOf(exception)

        val responseEntity =  ResponseEntity(envelopedResponse, ServerErrorCodes.INVALID_REQUEST.status)

        LOG.info(logPattern, e.message, responseEntity.toString())

        return responseEntity

    }
}
