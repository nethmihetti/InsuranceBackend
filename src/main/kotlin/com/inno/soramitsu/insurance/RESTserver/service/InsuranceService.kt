package com.inno.soramitsu.insurance.RESTserver.service

import com.inno.soramitsu.insurance.RESTserver.model.*

/**
 * Created by nethmih on 17.03.19.
 */

interface InsuranceService {
    fun getAllInsuranceBoxProducts() : List<Insurance>
    fun postNewUser(newUser: UserBody)
    fun postNewAddress(newAddress: AddressBody)
    fun insertNewInsuranceRequest(insuranceRequestBody: InsuranceRequestBody)

    fun getInsuranceRequestsForCompany(companyId: Long): List<Insurance>

    fun updateInsuranceStatus(insuranceId: Long, status: InsuranceStatusType)

    fun insertNewCompany(companyRequestBody: CompanyRequestBody)
}