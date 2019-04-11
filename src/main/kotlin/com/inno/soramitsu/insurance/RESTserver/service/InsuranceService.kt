package com.inno.soramitsu.insurance.RESTserver.service

import com.inno.soramitsu.insurance.RESTserver.model.AddressBody
import com.inno.soramitsu.insurance.RESTserver.model.Insurance
import com.inno.soramitsu.insurance.RESTserver.model.InsuranceRequestBody
import com.inno.soramitsu.insurance.RESTserver.model.UserBody

/**
 * Created by nethmih on 17.03.19.
 */

interface InsuranceService {
    fun getAllInsuranceBoxProducts() : List<Insurance>
    fun postNewUser(newUser: UserBody)
    fun postNewAddress(newAddress: AddressBody)
    fun insertNewInsuranceRequest(insuranceRequestBody: InsuranceRequestBody)
}