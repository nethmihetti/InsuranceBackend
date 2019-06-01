package com.inno.soramitsu.insurance.RESTserver.controller

import com.inno.soramitsu.insurance.RESTserver.model.*
import com.inno.soramitsu.insurance.RESTserver.model.envelope.EnvelopedResponse
import com.inno.soramitsu.insurance.RESTserver.service.AgentInsuranceService
import com.inno.soramitsu.insurance.RESTserver.service.AuthenticationService
import com.inno.soramitsu.insurance.RESTserver.service.impl.AuthenticationServiceImpl
import com.inno.soramitsu.insurance.RESTserver.util.ResponseUtil
import io.swagger.annotations.ApiParam
import org.springframework.data.domain.Page
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


/**
 * Created by nethmih on 14.04.19.
 */

@RestController
@RequestMapping("/api/V1/agents")
@CrossOrigin
class AgentController(private val agentInsuranceService: AgentInsuranceService) {

    @PostMapping("/companies")
    fun insertNewCompany(@RequestBody companyRequestBody: CompanyRequestBody) : ResponseEntity<EnvelopedResponse<Any>> {


        val company: Company = agentInsuranceService.insertNewCompany(companyRequestBody)

        val envelopedResponse: EnvelopedResponse<Any> = ResponseUtil.generateResponse(company)

        return ResponseEntity(envelopedResponse, HttpStatus.CREATED)

    }

    @PatchMapping("/requests")
    fun updateInsuranceStatus(@RequestParam insuranceId: Long, @RequestParam status: InsuranceStatusType) :
            ResponseEntity<EnvelopedResponse<Any>> {

        val insurance = agentInsuranceService.updateInsuranceStatus(insuranceId, status)

        val envelopedResponse: EnvelopedResponse<Any>  = ResponseUtil.generateResponse(insurance)

        return ResponseEntity(envelopedResponse, HttpStatus.ACCEPTED)

    }

    @GetMapping("/requests")
    fun getInsuranceRequests(@RequestParam companyId: Long, @RequestParam status: InsuranceStatusQueryType,
                             @RequestParam(value = "page", required = false) page: Int?,
                             @RequestParam(value = "size", required = false) size: Int?)
            : ResponseEntity<EnvelopedResponse<*>> {

        val requestTO = RequestTO
        requestTO.companyId = companyId
        requestTO.status = status
        requestTO.size = size
        requestTO.page = page

        val requests: Page<Insurance> = agentInsuranceService.getInsuranceRequestsForCompany(requestTO)

        val envelopedResponse: EnvelopedResponse<Any>  = ResponseUtil.generateResponse(requests)

        ResponseUtil.generateGetAllInsuranceRequestsLinks(requestTO, envelopedResponse)

        return ResponseEntity(envelopedResponse, HttpStatus.OK)
    }
}
