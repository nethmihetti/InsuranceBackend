package com.inno.soramitsu.insurance.RESTserver.service

import com.inno.soramitsu.insurance.RESTserver.model.Agent
import com.inno.soramitsu.insurance.RESTserver.model.AgentSignUpRequestBody

/**
 * Created by nethmih on 17.05.19.
 */

interface AuthenticationService {
    fun postNewAgent(agentSignUpRequestBody: AgentSignUpRequestBody) : Agent
}