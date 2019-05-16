package com.inno.soramitsu.insurance.RESTserver.service

import com.inno.soramitsu.insurance.RESTserver.model.Insurance
import com.inno.soramitsu.insurance.RESTserver.model.InsuranceRequestBody
import com.inno.soramitsu.insurance.RESTserver.model.User
import com.inno.soramitsu.insurance.RESTserver.model.UserBody

/**
 * Created by nethmih on 16.05.19.
 */

interface ClientInsuranceService {

    fun postNewUser(newUser: UserBody) : User

    fun insertNewInsuranceRequest(insuranceRequestBody: InsuranceRequestBody): Insurance

    fun getInsuranceRequestsForClient(email: String): List<Insurance>
}