package com.inno.soramitsu.insurance.RESTserver.service

import com.inno.soramitsu.insurance.RESTserver.controller.AddressBody
import com.inno.soramitsu.insurance.RESTserver.controller.UserBodyNew
import com.inno.soramitsu.insurance.RESTserver.model.Insurance
import com.inno.soramitsu.insurance.RESTserver.model.UserAddress
import com.inno.soramitsu.insurance.RESTserver.model.UserBody

/**
 * Created by nethmih on 17.03.19.
 */

interface InsuranceService {
    fun getAllInsuranceBoxProducts() : List<Insurance>
    fun postNewUser(newUser: UserBodyNew)
    fun postNewAddress(newAddress: AddressBody)
}