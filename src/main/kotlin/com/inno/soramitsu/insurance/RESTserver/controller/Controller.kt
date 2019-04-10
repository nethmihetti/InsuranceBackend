package com.inno.soramitsu.insurance.RESTserver.controller

import com.inno.soramitsu.insurance.RESTserver.model.Insurance
import com.inno.soramitsu.insurance.RESTserver.model.User
import com.inno.soramitsu.insurance.RESTserver.model.UserAddress
import com.inno.soramitsu.insurance.RESTserver.model.UserBody
import com.inno.soramitsu.insurance.RESTserver.service.InsuranceService
import org.apache.tomcat.jni.Address
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
    fun postNewUser(@RequestBody userBody: UserBodyNew) {
        insuranceService.postNewUser(userBody)
    }

    @PostMapping("/address")
    fun postNewAddress(@RequestBody addressBody: AddressBody) {

        System.out.println(addressBody)
        insuranceService.postNewAddress(addressBody)
    }
}


class AddressBody (
    var houseNum: String = "",
    var apartmentNum: String = "",
    var street: String = "",
    var city: String = "",
    var state: String = "",
    var country: String = ""
)

class UserBodyNew (
        var username: String = "",
        var firstName: String = "",
        var middleName: String = "",
        var lastName: String = "",
        var password: String = "",
        var email: String = "",
        var mobileNum: String = "",
        var passportNum: String = "",
        var passportIssuedBy: String = "",
        var passportIssuedDate: TimeStamp = ""

)