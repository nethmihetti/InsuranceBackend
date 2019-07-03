package com.inno.soramitsu.insurance.RESTserver.util

import com.inno.soramitsu.insurance.RESTserver.util.exception.ServerErrorCodes
import com.inno.soramitsu.insurance.RESTserver.util.exception.ServerExceptions
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.context.request.WebRequest
import java.lang.Exception
import java.util.*
import com.inno.soramitsu.insurance.RESTserver.model.envelope.EnvelopedResponse
import com.inno.soramitsu.insurance.RESTserver.util.exception.ServerErrorMessages
import org.mockito.Mock
import org.springframework.beans.TypeMismatchException
import org.springframework.web.servlet.NoHandlerFoundException
import java.beans.PropertyChangeEvent


/**
 * Created by nethmih on 03.07.19.
 */

class ServerExceptionHandlerTest {

    private lateinit var serverExceptionHandler: ServerExceptionHandler

    @Mock
    private lateinit var mockedWebRequest: WebRequest

    @Mock
    private lateinit var mockPropertyChangeEvent: PropertyChangeEvent

    @Before
    fun setup() {
        serverExceptionHandler = ServerExceptionHandler()

        mockedWebRequest = Mockito.mock(WebRequest::class.java)

        mockPropertyChangeEvent = Mockito.mock(PropertyChangeEvent::class.java)
    }

    @Test
    fun testHandleServerException() {

        val ex = ServerExceptions("config", ServerErrorCodes.SERVICE_FAILURE, "this is an error")

        val responseEntity = serverExceptionHandler.handleException(ex)
        assertNotNull(responseEntity)
        assertEquals(responseEntity.statusCode, ex.errorCode.status)
        assertTrue(responseEntity.body is EnvelopedResponse<*>)

        val envelopedResponse = responseEntity.body as EnvelopedResponse<*>

        assertEquals(envelopedResponse.error!!.size, 1)
        val serverExceptionTO = envelopedResponse.error!![0]
        assertEquals(serverExceptionTO.message, ex.message)
        assertEquals(serverExceptionTO.code, ex.errorCode.desc)
        assertEquals(serverExceptionTO.additionalInfo, ex.additionalInfo)
    }

    @Test
    fun testHandleException() {

        val ex = Exception("this is an error")

        val responseEntity = serverExceptionHandler.handleException(ex)
        assertNotNull(responseEntity)
        assertTrue(responseEntity.body is EnvelopedResponse<*>)

        val envelopedResponse = responseEntity.body as EnvelopedResponse<*>

        assertEquals(envelopedResponse.error!!.size, 1)
        val serverExceptionTO = envelopedResponse.error!![0]
        println(serverExceptionTO)
        assertEquals(serverExceptionTO.additionalInfo, ex.message)
    }

    @Test
    fun testHandleMissingServletRequestParameter() {
        val ex = MissingServletRequestParameterException(UUID.randomUUID().toString(), UUID.randomUUID().toString())

        val responseEntity = serverExceptionHandler.handleMissingServletRequestParameter(ex, HttpHeaders(),
                HttpStatus.ACCEPTED, mockedWebRequest)

        assertNotNull(responseEntity)
        assertEquals(responseEntity.statusCode, ServerErrorCodes.TYPE_INVALID.status)
        assertTrue(responseEntity.body is EnvelopedResponse<*>)

        val envelopedResponse = responseEntity.body as EnvelopedResponse<*>
        assertEquals(envelopedResponse.error!!.size, 1)
        val serverExceptionTO = envelopedResponse.error!![0]

        assertEquals(ServerErrorMessages.REQUIRED_PARAM_ERROR ,serverExceptionTO.code)
    }

    @Test
    fun testHandleTypeMismatch() {
        val ex = TypeMismatchException(mockPropertyChangeEvent, String::class.java)

        val responseEntity = serverExceptionHandler.handleTypeMismatch(ex, HttpHeaders(),
                HttpStatus.ACCEPTED, mockedWebRequest)

        assertNotNull(responseEntity)
        assertEquals(responseEntity.statusCode, ServerErrorCodes.TYPE_INVALID.status)
        assertTrue(responseEntity.body is EnvelopedResponse<*>)

        val envelopedResponse = responseEntity.body as EnvelopedResponse<*>
        assertEquals(envelopedResponse.error!!.size, 1)
        val serverExceptionTO = envelopedResponse.error!![0]

        assertEquals(ServerErrorCodes.TYPE_INVALID.desc ,serverExceptionTO.code)
    }

    @Test
    fun handleNoHandlerFoundException() {

        val ex = NoHandlerFoundException("", "", HttpHeaders())

        val responseEntity = serverExceptionHandler.handleNoHandlerFoundException(ex, HttpHeaders(),
                HttpStatus.ACCEPTED, mockedWebRequest)

        assertNotNull(responseEntity)
        assertEquals(responseEntity.statusCode, ServerErrorCodes.INVALID_REQUEST.status)
        assertTrue(responseEntity.body is EnvelopedResponse<*>)

        val envelopedResponse = responseEntity.body as EnvelopedResponse<*>
        assertEquals(envelopedResponse.error!!.size, 1)
        val serverExceptionTO = envelopedResponse.error!![0]

        assertEquals(ServerErrorCodes.INVALID_REQUEST.desc ,serverExceptionTO.code)

    }

    @Test
    fun testHandleSQLException() {

        val ex = Exception("this is an error")

        val responseEntity = serverExceptionHandler.handleSQLException(ex)

        assertNotNull(responseEntity)
        assertEquals(responseEntity.statusCode, ServerErrorCodes.CONFLICT.status)
        assertTrue(responseEntity.body is EnvelopedResponse<*>)

        val envelopedResponse = responseEntity.body as EnvelopedResponse<*>
        assertEquals(envelopedResponse.error!!.size, 1)
        val serverExceptionTO = envelopedResponse.error!![0]

        assertEquals(ServerErrorMessages.DUPLICATE_VALUE ,serverExceptionTO.code)
    }

    @Test
    fun testHandleSQLExceptionEmptyResults() {

        val ex = Exception("this is an error")

        val responseEntity = serverExceptionHandler.handleSQLExceptionEmptyResults(ex)

        assertNotNull(responseEntity)
        assertEquals(ServerErrorCodes.INVALID_REQUEST.status, responseEntity.statusCode)
        assertTrue(responseEntity.body is EnvelopedResponse<*>)

        val envelopedResponse = responseEntity.body as EnvelopedResponse<*>
        assertEquals(envelopedResponse.error!!.size, 1)
        val serverExceptionTO = envelopedResponse.error!![0]

        assertEquals(ServerErrorMessages.NULL_RESPONSE_FROM_DATABASE ,serverExceptionTO.code)
    }

}

