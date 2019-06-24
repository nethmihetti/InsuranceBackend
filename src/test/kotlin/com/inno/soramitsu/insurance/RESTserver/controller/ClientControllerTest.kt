package com.inno.soramitsu.insurance.RESTserver.controller

import com.inno.soramitsu.insurance.RESTserver.model.*
import com.inno.soramitsu.insurance.RESTserver.service.ClientInsuranceService
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import java.time.LocalDate


/**
 * Created by nethmih on 04.04.19.
 */


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(MockitoJUnitRunner::class)
class ClientControllerTest {

    private lateinit var clientController: ClientController

    @Mock
    private lateinit var clientInsuranceService: ClientInsuranceService

    @Before
    fun setup() {
        clientController = ClientController(clientInsuranceService)
    }


    @Test
    fun testGetInsuranceRequestForClient() {

        val address = UserAddress(1, "123", "Dorm3", "1,University st", "Innopolis", "Tatarstan", "Russia")
        val mockCompany = Company(1, "ABC Insurance", address)
        val mockInsuranceUsers = InsuranceUsers(1, "Jon", "password")
        val mockClient = Client(2, "Jon", "nomiddlename", "snow", "jonsnow@gmail.com",
                "+78957475", "123658", "Winterfell", LocalDate.parse("2018-12-31"), mockInsuranceUsers)

        val insuranceMock = Insurance(12, "some title", 1000.00, LocalDate.parse("2018-12-31"),
                LocalDate.parse("2018-12-31"), LocalDate.parse("2018-12-31"), "pending", address, mockClient, mockCompany)

        val insuranceRequests = listOf(insuranceMock)

        val pagedInsuranceRequests: Page<Insurance> = PageImpl<Insurance>(insuranceRequests, PageRequest(0, 10), insuranceRequests.size.toLong())

        val requestTO = RequestTO
        requestTO.email = "jonsnow@gmail.com"
        requestTO.companyId = 1L
        requestTO.page = 0
        requestTO.size = 10
        requestTO.status = listOf(InsuranceStatusQueryType.ACCEPTED)

        given(clientInsuranceService.getInsuranceRequestsForClient(requestTO)).willReturn(pagedInsuranceRequests)

        val response = clientController.getInsuranceRequestForClient("jonsnow@gmail.com", 0, 10)

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)

    }
}
