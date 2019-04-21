package com.inno.soramitsu.insurance.RESTserver.controller

import com.inno.soramitsu.insurance.RESTserver.model.*
import com.inno.soramitsu.insurance.RESTserver.model.envelope.EnvelopedResponse
import com.inno.soramitsu.insurance.RESTserver.service.InsuranceService
import com.inno.soramitsu.insurance.RESTserver.util.ResponseUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Created by nethmih on 17.03.19.
 */

@RestController
@RequestMapping("/api/V1/clients")
class ClientController {

    @Autowired
    lateinit var insuranceService: InsuranceService

    @PostMapping("/users")
    fun postNewUser(@RequestBody userBody: UserBody) {

            insuranceService.postNewUser(userBody)

    }

    @PostMapping("/requests")
    fun postInsuranceRequest(@RequestBody insuranceRequestBody: InsuranceRequestBody):ResponseEntity<EnvelopedResponse<Any>> {

        val request: Insurance = insuranceService.insertNewInsuranceRequest(insuranceRequestBody)

        val envelopedResponse: EnvelopedResponse<Any> = ResponseUtil.generateResponse(request)

        return ResponseEntity(envelopedResponse, HttpStatus.CREATED)

    }

    @GetMapping("/requests")
    fun getInsuranceRequestForClient(@RequestParam email: String ) : ResponseEntity<EnvelopedResponse<Any>> {

        val requests: List<Insurance>  = insuranceService.getInsuranceRequestsForClient(email)
        val envelopedResponse: EnvelopedResponse<Any> = ResponseUtil.generateResponse(requests)

        return ResponseEntity(envelopedResponse, HttpStatus.OK)

    }

}


