package com.inno.soramitsu.insurance.RESTserver.controller.service

import com.inno.soramitsu.insurance.RESTserver.dao.AddressRepository
import com.inno.soramitsu.insurance.RESTserver.dao.ClaimRepository
import com.inno.soramitsu.insurance.RESTserver.dao.CompanyRepository
import com.inno.soramitsu.insurance.RESTserver.dao.InsuranceRepository
import com.inno.soramitsu.insurance.RESTserver.model.*
import com.inno.soramitsu.insurance.RESTserver.service.impl.AgentInsuranceServiceImpl
import com.inno.soramitsu.insurance.RESTserver.util.ServerUtil
import com.inno.soramitsu.insurance.RESTserver.util.exception.ServerExceptions
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.doNothing
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.time.LocalDate

/**
 * Created by nethmih on 02.07.19.
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(MockitoJUnitRunner.Silent::class)
class AgentInsuranceServiceTest {

    private lateinit var agentInsuranceService: AgentInsuranceServiceImpl

    @Mock
    private lateinit var insuranceRepository: InsuranceRepository

    @Mock
    private lateinit var addressRepository: AddressRepository

    @Mock
    private lateinit var companyRepository: CompanyRepository

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
        agentInsuranceService = AgentInsuranceServiceImpl(insuranceRepository, addressRepository, companyRepository, claimRepository)

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
    fun testGetInsuranceRequestsForCompanyForAllStatus() {

        val insuranceRequests = listOf(insuranceMock)

        val pagedInsuranceRequests: Page<Insurance> = PageImpl(insuranceRequests, PageRequest(0, 10), insuranceRequests.size.toLong())


        val requestTO = RequestTO
        requestTO.email = "jonsnow@gmail.com"
        requestTO.companyId = 1L
        requestTO.page = 0
        requestTO.size = 10
        requestTO.status = listOf(InsuranceStatusQueryType.ACCEPTED, InsuranceStatusQueryType.ALL)

        given(insuranceRepository.findByCompanyCompanyidOrderByInsurancerequestidDesc(requestTO.companyId,
                ServerUtil.getPageSize(requestTO.page, requestTO.size))).willReturn(pagedInsuranceRequests)


        val response = agentInsuranceService.getInsuranceRequestsForCompany(requestTO)

        assertNotNull(response)
        assertEquals(response.content[0].insurancerequestid, 12)
        assertEquals(response.content[0].status, "pending")

    }

    @Test
    fun testGetInsuranceRequestsForCompanyForOtherStatus() {

        val insuranceRequests = listOf(insuranceMock)

        val pagedInsuranceRequests: Page<Insurance> = PageImpl(insuranceRequests, PageRequest(0, 10), insuranceRequests.size.toLong())


        val requestTO = RequestTO
        requestTO.email = "jonsnow@gmail.com"
        requestTO.companyId = 1L
        requestTO.page = 0
        requestTO.size = 10
        requestTO.status = listOf(InsuranceStatusQueryType.ACCEPTED, InsuranceStatusQueryType.PENDING)

        given(insuranceRepository.findByCompanyCompanyidAndStatusInOrderByInsurancerequestidDesc(requestTO.companyId,
                mutableListOf(),
                ServerUtil.getPageSize(requestTO.page, requestTO.size))).willReturn(pagedInsuranceRequests)


        val response = agentInsuranceService.getInsuranceRequestsForCompany(requestTO)

        assertNull(response)

    }

    @Test
    fun testUpdateInsuranceStatus() {
        doNothing().`when`(insuranceRepository).updateInsuranceStatus(Mockito.anyLong(), Mockito.anyString())
        given(insuranceRepository.findByInsuranceRequestId(Mockito.anyLong())).willReturn(insuranceMock)

        val response = agentInsuranceService.updateInsuranceStatus(1L, InsuranceStatusType.ACCEPTED)

        assertNotNull(response)
        assertEquals(response.insurancerequestid, 12)

    }

    @Test
    fun testInsertNewCompany() {
        given(addressRepository.save(Mockito.any(UserAddress::class.java))).willReturn(address)
        given(companyRepository.save(Mockito.any(Company::class.java))).willReturn(mockCompany)

        val response = agentInsuranceService.insertNewCompany(companyRequestBody)

        assertNotNull(response)
        assertEquals(response.companyid, 1)
    }

    @Test(expected = ServerExceptions::class)
    fun testInsertNewCompanyWithWrongAddress() {

        address = UserAddress(-1, "", "", " st", "", "", "")

        given(addressRepository.save(Mockito.any(UserAddress::class.java))).willReturn(address)

        agentInsuranceService.insertNewCompany(companyRequestBody)

    }

    @Test
    fun testGetInsuranceClaimsForCompany() {

        val insuranceClaims = listOf(claimMock)

        val pagedInsuranceClaims: Page<InsuranceClaim> = PageImpl(insuranceClaims, PageRequest(0, 10), insuranceClaims.size.toLong())


        val requestTO = RequestTO
        requestTO.email = "jonsnow@gmail.com"
        requestTO.companyId = 1L
        requestTO.page = 0
        requestTO.size = 10
        requestTO.status = listOf(InsuranceStatusQueryType.ALL)

        given(claimRepository.findByInsuranceCompanyCompanyidOrderByClaimedDateDesc(requestTO.companyId,
                ServerUtil.getPageSize(requestTO.page, requestTO.size))).willReturn(pagedInsuranceClaims)


        val response = agentInsuranceService.getInsuranceClaimsForCompany(requestTO)

        assertNotNull(response)
        assertEquals(response.content[0].insurance.insurancerequestid, 12)

    }

    @Test
    fun testGetInsuranceClaimsForCompanyForOtherStatus() {

        val insuranceClaims = listOf(claimMock)

        val pagedInsuranceClaims: Page<InsuranceClaim> = PageImpl(insuranceClaims, PageRequest(0, 10), insuranceClaims.size.toLong())


        val requestTO = RequestTO
        requestTO.email = "jonsnow@gmail.com"
        requestTO.companyId = 1L
        requestTO.page = 0
        requestTO.size = 10
        requestTO.status = listOf(InsuranceStatusQueryType.PENDING, InsuranceStatusQueryType.REJECTED)

        given(claimRepository.findByInsuranceCompanyCompanyidAndStatusInOrderByClaimedDateDesc(requestTO.companyId, mutableListOf(),
                ServerUtil.getPageSize(requestTO.page, requestTO.size))).willReturn(pagedInsuranceClaims)


        val response = agentInsuranceService.getInsuranceClaimsForCompany(requestTO)

        assertNull(response)

    }

    @Test
    fun testUpdateInsuranceClaimStatus() {
        doNothing().`when`(claimRepository).updateInsuranceClaimStatus(Mockito.anyLong(), Mockito.anyString())
        given(claimRepository.findByClaimid(Mockito.anyLong())).willReturn(claimMock)

        val response = agentInsuranceService.updateInsuranceClaimStatus(1L, InsuranceStatusType.ACCEPTED)

        assertNotNull(response)
        assertEquals(response.claimid, 3L)
    }


}