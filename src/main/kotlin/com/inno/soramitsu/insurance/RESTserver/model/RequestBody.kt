package com.inno.soramitsu.insurance.RESTserver.model

import java.util.*

/**
 * Created by nethmih on 10.04.19.
 */


class AddressBody (
        var houseNum: String = "",
        var apartmentNum: String = "",
        var street: String = "",
        var city: String = "",
        var state: String = "",
        var country: String = ""
)

class UserBody (
        var username: String = "",
        var firstName: String = "",
        var middleName: String = "",
        var lastName: String = "",
        var password: String = "",
        var email: String = "",
        var mobileNum: String = "",
        var passportNum: String = "",
        var passportIssuedBy: String = "",
        var passportIssuedDate: Date

)

enum class InsuranceStatusType(val type: String) {
    PENDING("pending"),
    ACCEPTED("accepted"),
    REJECTED("rejected")
}


class InsuranceRequestBody (
    var username: String = "",
    var propertyType: String= "",
    var amount: Double=0.00,
    var policyStartDate: Date,
    var policyEndDate: Date,
    var policyCreatedDate: Date,
    var address: AddressBody,
    var status: String=InsuranceStatusType.PENDING.type,
    var companyId: Long=1
)

class CompanyRequestBody(
    var CompanyName: String ="",
    var address: AddressBody
)