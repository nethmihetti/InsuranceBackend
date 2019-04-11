package com.inno.soramitsu.insurance.RESTserver.controller

import com.inno.soramitsu.insurance.RESTserver.model.AddressBody
import com.inno.soramitsu.insurance.RESTserver.model.InsuranceRequestBody
import com.inno.soramitsu.insurance.RESTserver.model.UserBody
import com.inno.soramitsu.insurance.RESTserver.service.InsuranceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * Created by nethmih on 17.03.19.
 */

@RestController
@RequestMapping("/insurance")
class Controller {

    @Autowired
    lateinit var insuranceService: InsuranceService

    @PostMapping("/user")
    fun postNewUser(@RequestBody userBody: UserBody) {
        try {
            insuranceService.postNewUser(userBody)
        } catch (ex: Exception) {
            System.out.println(ex)
        }

    }

    @PostMapping("/address")
    fun postNewAddress(@RequestBody addressBody: AddressBody) {

        System.out.println(addressBody)
        insuranceService.postNewAddress(addressBody)
    }

    @PostMapping("/request")
    fun postInsuranceRequest(@RequestBody insuranceRequestBody: InsuranceRequestBody) {
        try {
            insuranceService.insertNewInsuranceRequest(insuranceRequestBody)
        } catch (ex: Exception) {
            System.out.println(ex)
        }
    }
}


