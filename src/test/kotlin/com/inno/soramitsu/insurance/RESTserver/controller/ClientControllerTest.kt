package com.inno.soramitsu.insurance.RESTserver.controller

import com.inno.soramitsu.insurance.RESTserver.model.*
import com.inno.soramitsu.insurance.RESTserver.service.ClientInsuranceService
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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
import java.util.*


/**
 * Created by nethmih on 04.04.19.
 */


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(MockitoJUnitRunner::class)
class ClientControllerTest {

    private lateinit var clientController: ClientController

    @Mock
    private lateinit var clientInsuranceService: ClientInsuranceService

    private lateinit var address: UserAddress

    private lateinit var mockCompany: Company

    private lateinit var mockInsuranceUsers: InsuranceUsers

    private lateinit var mockClient: Client

    private lateinit var insuranceMock: Insurance

    private lateinit var claimMock: InsuranceClaim

    private lateinit var addressBody: AddressBody

    @Before
    fun setup() {
        clientController = ClientController(clientInsuranceService)

        address = UserAddress(1, "123", "Dorm3", "1,University st", "Innopolis", "Tatarstan", "Russia")
        mockCompany = Company(1, "ABC Insurance", "some description", "some url", address)
        mockInsuranceUsers = InsuranceUsers(1, "Jon", "password")
        mockClient = Client(2, "Jon", "nomiddlename", "snow", "jonsnow@gmail.com",
                "+78957475", "123658", "Winterfell", LocalDate.parse("2018-12-31"), mockInsuranceUsers)

        insuranceMock = Insurance(12, "some title", 1000.00, LocalDate.parse("2018-12-31"),
                LocalDate.parse("2018-12-31"), LocalDate.parse("2018-12-31"), "pending", address, mockClient, mockCompany)

        claimMock = InsuranceClaim(3, "some description", LocalDate.now(), "PENDING", insuranceMock )

        addressBody = AddressBody("12", "33", "some street", "this city", "that state", "obvious")
    }

    @Test
    fun testUpdateClientDetails() {
        val userBody = UserBody("jon", "bastered", "snow", "jonsnow@gmail.com",
                "02145555", "11112", "Winterfell", Date())

        given(clientInsuranceService.updateClientDetails(userBody)).willReturn(mockClient)

        val response = clientController.updateClientDetails(userBody)

        assertNotNull(response)
        assertEquals(HttpStatus.ACCEPTED, response.statusCode)
    }

    @Test
    fun testPostInsuranceRequest() {

        val insuranceRequestBody = InsuranceRequestBody("jonsnow@gmail.com", "Vehicle", "1000", Date(), Date(), addressBody, 1 )

        given(clientInsuranceService.insertNewInsuranceRequest(insuranceRequestBody)).willReturn(insuranceMock)

        val response = clientController.postInsuranceRequest(insuranceRequestBody)

        assertNotNull(response)
        assertEquals(HttpStatus.CREATED, response.statusCode)

    }


    @Test
    fun testGetInsuranceRequestForClient() {
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

    @Test
    fun testPostInsuranceClaim() {

        val insuranceClaimBody = InsuranceClaimBody(2, "some description", Date())

        given(clientInsuranceService.insertNewInsuranceClaim(insuranceClaimBody)).willReturn(claimMock)

        val response = clientController.postInsuranceClaim(insuranceClaimBody)

        assertNotNull(response)
        assertEquals(HttpStatus.CREATED, response.statusCode)

    }

    @Test
    fun testGetInsuranceClaimsForClient() {
        val insuranceClaims = listOf(claimMock)

        val pagedInsuranceClaims: Page<InsuranceClaim> = PageImpl<InsuranceClaim>(insuranceClaims, PageRequest(0, 10), insuranceClaims.size.toLong())

        val requestTO = RequestTO
        requestTO.email = "jonsnow@gmail.com"
        requestTO.companyId = 1L
        requestTO.page = 0
        requestTO.size = 10
        requestTO.status = listOf(InsuranceStatusQueryType.ACCEPTED)

        given(clientInsuranceService.getInsuranceClaimsForClient(requestTO)).willReturn(pagedInsuranceClaims)

        val response = clientController.getInsuranceClaimsForClient("jonsnow@gmail.com", 0, 10)

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)

    }

    @Test
    fun testGetAllCompanyDetails() {
        given(clientInsuranceService.getAllCompanyDetails()).willReturn(listOf(mockCompany))

        val response = clientController.getAllCompanyDetails()

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
    }
}
