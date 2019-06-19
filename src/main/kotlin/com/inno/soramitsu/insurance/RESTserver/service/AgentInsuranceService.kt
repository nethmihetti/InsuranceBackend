package com.inno.soramitsu.insurance.RESTserver.service

import com.inno.soramitsu.insurance.RESTserver.model.*
import org.springframework.data.domain.Page

/**
 * Created by nethmih on 17.03.19.
 */

interface AgentInsuranceService {

    fun getInsuranceRequestsForCompany(requestTO: RequestTO): Page<Insurance>

    fun updateInsuranceStatus(insuranceId: Long, status: InsuranceStatusType): Insurance

    fun insertNewCompany(companyRequestBody: CompanyRequestBody): Company

    fun getInsuranceClaimsForCompany(requestTO: RequestTO): Page<InsuranceClaim>

   // fun postNewAgent(agentSignUpRequestBody: AgentSignUpRequestBody) : Agent

}