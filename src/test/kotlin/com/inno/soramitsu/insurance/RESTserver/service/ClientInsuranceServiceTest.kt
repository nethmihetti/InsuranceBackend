package com.inno.soramitsu.insurance.RESTserver.service

import com.inno.soramitsu.insurance.RESTserver.dao.*
import com.inno.soramitsu.insurance.RESTserver.model.*
import com.inno.soramitsu.insurance.RESTserver.service.impl.AgentInsuranceServiceImpl
import com.inno.soramitsu.insurance.RESTserver.service.impl.ClientInsuranceServiceImpl
import com.inno.soramitsu.insurance.RESTserver.util.ServerUtil
import com.inno.soramitsu.insurance.RESTserver.util.exception.ServerExceptions
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

/**
 * Created by nethmih on 02.07.19.
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(MockitoJUnitRunner.Silent::class)
class ClientInsuranceServiceTest {

    private lateinit var clientInsuranceService: ClientInsuranceServiceImpl

    @Mock
    private lateinit var insuranceRepository: InsuranceRepository

    @Mock
    private lateinit var addressRepository: AddressRepository

    @Mock
    private lateinit var companyRepository: CompanyRepository

    @Mock
    private lateinit var clientRepository: ClientRepository

    @Mock
    private lateinit var claimRepository: ClaimRepository

    private lateinit var address: UserAddress

    private lateinit var mockCompany: Company

    private lateinit var mockInsuranceUsers: InsuranceUsers

    private lateinit var mockClient: Client

    private lateinit var insuranceMock: Insurance

    private lateinit var claimMock: InsuranceClaim

    private lateinit var addressBody: AddressBody

    private lateinit var companyRequestBody: CompanyRequestBody

    @Before
    fun setUp() {
        clientInsuranceService = ClientInsuranceServiceImpl(insuranceRepository, addressRepository, companyRepository,
                clientRepository, claimRepository)

        address = UserAddress(1, "123", "Dorm3", "1,University st", "Innopolis", "Tatarstan", "Russia")
        mockCompany = Company(1, "ABC Insurance", "some description", "some url", address)
        mockInsuranceUsers = InsuranceUsers(1, "Jon", "password")
        mockClient = Client(2, "Jon", "nomiddlename", "snow", "jonsnow@gmail.com",
                "+78957475", "123658", "Winterfell", LocalDate.parse("2018-12-31"), mockInsuranceUsers)

        insuranceMock = Insurance(12, "some title", 1000.00, LocalDate.parse("2018-12-31"),
                LocalDate.parse("2018-12-31"), LocalDate.parse("2018-12-31"), "pending", address, mockClient, mockCompany)

        claimMock = InsuranceClaim(3, "some description", LocalDate.now(), "PENDING", insuranceMock)

        addressBody = AddressBody("12", "33", "some street", "this city", "that state", "obvious")

        companyRequestBody = CompanyRequestBody("ABC Company", "some description", "http://some_url", addressBody)
    }

    @Test
    fun testUpdateClientDetails() {
        val userBody = UserBody("jon", "bastered", "snow", "jonsnow@gmail.com",
                "02145555", "11112", "Winterfell", Date())

        given(clientRepository.findByEmail(Mockito.anyString())).willReturn(mockClient)
        given(clientRepository.save(Mockito.any(Client::class.java))).willReturn(mockClient)

        val response = clientInsuranceService.updateClientDetails(userBody)

        assertEquals(response.first_name, "jon")

    }

    @Test
    fun testInsertNewInsuranceRequestWithExistingDetails() {
        val insuranceRequestBody = InsuranceRequestBody("jonsnow@gmail.com", "House", "100",
                Date(),Date.from(LocalDate.parse("2020-02-14").atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
                , addressBody, 1L)

        given(clientRepository.findByEmail(Mockito.anyString())).willReturn(mockClient)
        given(addressRepository.getAddressCount(addressBody)).willReturn(1)
        given(addressRepository.getAddressDetails(addressBody)).willReturn(address)
        given(companyRepository.findByCompanyid(Mockito.anyLong())).willReturn(mockCompany)
        given(insuranceRepository.save(Mockito.any(Insurance::class.java))).willReturn(insuranceMock)

        val response = clientInsuranceService.insertNewInsuranceRequest(insuranceRequestBody)

        assertNotNull(response)
        assertEquals(response.insurancerequestid, 12)
        assertEquals(response.status, "pending")

    }

    @Test
    fun testInsertNewInsuranceRequestWithNewAddress() {
        val insuranceRequestBody = InsuranceRequestBody("jonsnow@gmail.com", "House", "100",
                Date(),Date.from(LocalDate.parse("2020-02-14").atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
                , addressBody, 1L)

        given(clientRepository.findByEmail(Mockito.anyString())).willReturn(mockClient)
        given(addressRepository.getAddressCount(addressBody)).willReturn(0)
        given(companyRepository.findByCompanyid(Mockito.anyLong())).willReturn(mockCompany)
        given(insuranceRepository.save(Mockito.any(Insurance::class.java))).willReturn(insuranceMock)
        given(addressRepository.save(Mockito.any(UserAddress::class.java))).willReturn(address)

        val response = clientInsuranceService.insertNewInsuranceRequest(insuranceRequestBody)

        assertNotNull(response)
        assertEquals(response.insurancerequestid, 12)
        assertEquals(response.status, "pending")

    }

    @Test(expected = ServerExceptions::class)
    fun testInsertNewInsuranceRequestWithNoExistingUser() {
        val insuranceRequestBody = InsuranceRequestBody("jonsnow@gmail.com", "House", "100",
                Date(),Date.from(LocalDate.parse("2020-02-14").atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
                , addressBody, 1L)

        mockClient = Client(0, "", "", "", "",
                "+", "", "", LocalDate.parse("2018-12-31"), mockInsuranceUsers)

        given(clientRepository.findByEmail(Mockito.anyString())).willReturn(mockClient)

        clientInsuranceService.insertNewInsuranceRequest(insuranceRequestBody)

    }

    @Test(expected = ServerExceptions::class)
    fun testInsertNewInsuranceRequestWithInvalidDates1() {
        val insuranceRequestBody = InsuranceRequestBody("jonsnow@gmail.com", "House", "100",
                Date(),Date.from(LocalDate.parse("2011-02-14").atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()), addressBody, 1L)

        clientInsuranceService.insertNewInsuranceRequest(insuranceRequestBody)

    }

    @Test(expected = ServerExceptions::class)
    fun testInsertNewInsuranceRequestWithInvalidDates2() {
        val insuranceRequestBody = InsuranceRequestBody("jonsnow@gmail.com", "House", "100",
                Date(),Date(), addressBody, 1L)

        clientInsuranceService.insertNewInsuranceRequest(insuranceRequestBody)

    }

    @Test
    fun testGetInsuranceRequestsForClient() {
        val insuranceRequests = listOf(insuranceMock)

        val pagedInsuranceRequests: Page<Insurance> = PageImpl(insuranceRequests, PageRequest(0, 10), insuranceRequests.size.toLong())


        val requestTO = RequestTO
        requestTO.email = "jonsnow@gmail.com"
        requestTO.companyId = 1L
        requestTO.page = 0
        requestTO.size = 10
        requestTO.status = listOf(InsuranceStatusQueryType.ALL)

        given(insuranceRepository.findByClientEmailOrderByInsurancerequestidDesc(requestTO.email,
                ServerUtil.getPageSize(requestTO.page, requestTO.size))).willReturn(pagedInsuranceRequests)


        val response = clientInsuranceService.getInsuranceRequestsForClient(requestTO)

        assertNotNull(response)
        assertEquals(response.content[0].insurancerequestid, 12)
    }

    @Test
    fun testGetInsuranceClaimsForClient() {
        val insuranceClaims = listOf(claimMock)

        val pagedInsuranceClaims: Page<InsuranceClaim> = PageImpl(insuranceClaims, PageRequest(0, 10), insuranceClaims.size.toLong())


        val requestTO = RequestTO
        requestTO.email = "jonsnow@gmail.com"
        requestTO.companyId = 1L
        requestTO.page = 0
        requestTO.size = 10
        requestTO.status = listOf(InsuranceStatusQueryType.ALL)

        given(claimRepository.findByInsuranceClientEmailOrderByClaimedDateDesc(requestTO.email,
                ServerUtil.getPageSize(requestTO.page, requestTO.size))).willReturn(pagedInsuranceClaims)


        val response = clientInsuranceService.getInsuranceClaimsForClient(requestTO)

        assertNotNull(response)
        assertEquals(response.content[0].insurance.insurancerequestid, 12)
        assertEquals(3L, response.content[0].claimid)
        assertEquals("PENDING", response.content[0].status)
    }

    @Test
    fun testInsertNewInsuranceClaim() {
        val claimBody = InsuranceClaimBody(1L, "this is description", Date())

        given(insuranceRepository.findByInsuranceRequestId(claimBody.policyId)).willReturn(insuranceMock)
        given(claimRepository.save(Mockito.any(InsuranceClaim::class.java))).willReturn(claimMock)

        val response = clientInsuranceService.insertNewInsuranceClaim(claimBody)

        assertNotNull(response)
        assertEquals(response.insurance.insurancerequestid, 12)

    }

    @Test(expected = ServerExceptions::class)
    fun testInsertNewInsuranceClaimWithErrorPolicy() {
        val claimBody = InsuranceClaimBody(1L, "this is description", Date())

        insuranceMock = Insurance(0, "", 0.00, LocalDate.parse("2018-12-31"),
                LocalDate.parse("2018-12-31"), LocalDate.parse("2018-12-31"), "pending", address, mockClient, mockCompany)

        given(insuranceRepository.findByInsuranceRequestId(claimBody.policyId)).willReturn(insuranceMock)

        clientInsuranceService.insertNewInsuranceClaim(claimBody)


    }

    @Test
    fun testGetAllCompanyDetails() {
        given(companyRepository.findAll()).willReturn(listOf(mockCompany))

        val response = clientInsuranceService.getAllCompanyDetails()

        assertNotNull(response)
        assertEquals(response[0].companyid, 1)
    }

}