package com.inno.soramitsu.insurance.RESTserver.controller

import com.inno.soramitsu.insurance.RESTserver.model.Agent
import com.inno.soramitsu.insurance.RESTserver.model.AgentSignUpRequestBody
import com.inno.soramitsu.insurance.RESTserver.model.envelope.EnvelopedResponse
import com.inno.soramitsu.insurance.RESTserver.service.AuthenticationService
import com.inno.soramitsu.insurance.RESTserver.util.ResponseUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

/**
 * Created by nethmih on 17.05.19.
 */

@RestController
@RequestMapping("/api/V1")
@CrossOrigin
class AuthenticationController(private val authenticationService: AuthenticationService,
                               private val bCryptPasswordEncoder: BCryptPasswordEncoder) {

    @PostMapping("/signUp")
    fun postNewAgent(@RequestBody agentSignUpRequestBody: AgentSignUpRequestBody): ResponseEntity<EnvelopedResponse<Any>> {

        agentSignUpRequestBody.password = bCryptPasswordEncoder.encode(agentSignUpRequestBody.password)

        val agent: Agent = authenticationService.postNewAgent(agentSignUpRequestBody)

        val envelopedResponse: EnvelopedResponse<Any> = ResponseUtil.generateResponse(agent)

        return ResponseEntity(envelopedResponse, HttpStatus.CREATED)

    }
}