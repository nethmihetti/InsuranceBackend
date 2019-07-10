package com.inno.soramitsu.insurance.RESTserver.service

import com.inno.soramitsu.insurance.RESTserver.model.Agent
import com.inno.soramitsu.insurance.RESTserver.model.SignUpRequestBody
import com.inno.soramitsu.insurance.RESTserver.model.Client

/**
 * Created by nethmih on 17.05.19.
 */

interface AuthenticationService {
    fun postNewAgent(agentSignUpRequestBody: SignUpRequestBody, companyId: Int) : Agent

    fun postNewClient(clientSignUpRequestBody: SignUpRequestBody) : Client

    fun getAgentDetails(agentUsername: String): Agent
}