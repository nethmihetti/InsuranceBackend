package com.inno.soramitsu.insurance.RESTserver.controller

import com.inno.soramitsu.insurance.RESTserver.model.*
import com.inno.soramitsu.insurance.RESTserver.model.envelope.EnvelopedResponse
import com.inno.soramitsu.insurance.RESTserver.service.ClientInsuranceService
import com.inno.soramitsu.insurance.RESTserver.util.ResponseUtil
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Created by nethmih on 17.03.19.
 */

@RestController
@RequestMapping("/api/V1/clients")
@CrossOrigin
class ClientController(private val clientInsuranceService: ClientInsuranceService) {

    @PutMapping
    fun updateClientDetails(@RequestBody userBody: UserBody):ResponseEntity<EnvelopedResponse<Any>> {


        val client: Client = clientInsuranceService.updateClientDetails(userBody)

        val envelopedResponse: EnvelopedResponse<Any> = ResponseUtil.generateResponse(client)

        return ResponseEntity(envelopedResponse, HttpStatus.ACCEPTED)

    }

    @PostMapping("/requests")
    fun postInsuranceRequest(@RequestBody insuranceRequestBody: InsuranceRequestBody):ResponseEntity<EnvelopedResponse<Any>> {

        val request: Insurance = clientInsuranceService.insertNewInsuranceRequest(insuranceRequestBody)

        val envelopedResponse: EnvelopedResponse<Any> = ResponseUtil.generateResponse(request)

        return ResponseEntity(envelopedResponse, HttpStatus.CREATED)

    }

    @GetMapping("/requests")
    fun getInsuranceRequestForClient(@RequestParam email: String,
                                     @RequestParam(value = "page", required = false) page: Int?,
                                     @RequestParam(value = "size", required = false) size: Int?)
            : ResponseEntity<EnvelopedResponse<Any>> {

        val requestTO = RequestTO
        requestTO.email = email
        requestTO.size = size
        requestTO.page = page

        val requests: Page<Insurance> = clientInsuranceService.getInsuranceRequestsForClient(requestTO)
        val envelopedResponse: EnvelopedResponse<Any> = ResponseUtil.generateResponse(requests)

        ResponseUtil.generateGetAllInsuranceRequestsLinksForClient(requestTO, envelopedResponse)

        return ResponseEntity(envelopedResponse, HttpStatus.OK)

    }

    @PostMapping("/claims")
    fun postInsuranceClaim(@RequestBody claimBody: InsuranceClaimBody): ResponseEntity<EnvelopedResponse<Any>> {

        val claim: InsuranceClaim = clientInsuranceService.insertNewInsuranceClaim(claimBody)

        val envelopedResponse: EnvelopedResponse<Any> = ResponseUtil.generateResponse(claim)

        return ResponseEntity(envelopedResponse, HttpStatus.CREATED)

    }

}


