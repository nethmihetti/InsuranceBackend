package com.inno.soramitsu.insurance.RESTserver.service

import com.inno.soramitsu.insurance.RESTserver.model.*

/**
 * Created by nethmih on 17.03.19.
 */

interface InsuranceService {
    fun getAllInsuranceBoxProducts() : List<Insurance>
    fun postNewUser(newUser: UserBody)
    fun insertNewInsuranceRequest(insuranceRequestBody: InsuranceRequestBody): Insurance

    fun getInsuranceRequestsForCompany(companyId: Long, status: InsuranceStatusQueryType): List<Insurance>

    fun updateInsuranceStatus(insuranceId: Long, status: InsuranceStatusType): Insurance

    fun insertNewCompany(companyRequestBody: CompanyRequestBody)

    fun getInsuranceRequestsForClient(email: String): List<Insurance>
}