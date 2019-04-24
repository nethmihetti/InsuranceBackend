package com.inno.soramitsu.insurance.RESTserver.controller

import com.inno.soramitsu.insurance.RESTserver.model.CompanyRequestBody
import com.inno.soramitsu.insurance.RESTserver.model.Insurance
import com.inno.soramitsu.insurance.RESTserver.model.InsuranceStatusQueryType
import com.inno.soramitsu.insurance.RESTserver.model.InsuranceStatusType
import com.inno.soramitsu.insurance.RESTserver.model.envelope.EnvelopedResponse
import com.inno.soramitsu.insurance.RESTserver.service.InsuranceService
import com.inno.soramitsu.insurance.RESTserver.util.ResponseUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


/**
 * Created by nethmih on 14.04.19.
 */


@RestController
@RequestMapping("/api/V1/agents")
@CrossOrigin
class AgentController {

    @Autowired
    lateinit var insuranceService: InsuranceService

    @PostMapping("/companies")
    fun insertNewCompany(@RequestBody companyRequestBody: CompanyRequestBody) {
        insuranceService.insertNewCompany(companyRequestBody)

    }

    @PatchMapping("/requests")
    fun updateInsuranceStatus(@RequestParam insuranceId: Long, @RequestParam status: InsuranceStatusType) :
            ResponseEntity<EnvelopedResponse<Any>> {

        val insurance = insuranceService.updateInsuranceStatus(insuranceId, status)

        val envelopedResponse: EnvelopedResponse<Any>  = ResponseUtil.generateResponse(insurance)

        return ResponseEntity(envelopedResponse, HttpStatus.ACCEPTED)

    }

    @GetMapping("/requests")
    fun getInsuranceRequests(@RequestParam companyId: Long, @RequestParam status: InsuranceStatusQueryType)
            : ResponseEntity<EnvelopedResponse<Any>> {

        val requests: List<Insurance> = insuranceService.getInsuranceRequestsForCompany(companyId, status)

        val envelopedResponse: EnvelopedResponse<Any>  = ResponseUtil.generateResponse(requests)

        return ResponseEntity(envelopedResponse, HttpStatus.OK)
    }
}
