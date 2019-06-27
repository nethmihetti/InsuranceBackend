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
        var firstName: String = "",
        var middleName: String = "",
        var lastName: String = "",
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

enum class InsuranceStatusQueryType(val type: String) {
    PENDING("pending"),
    ACCEPTED("accepted"),
    REJECTED("rejected"),
    ALL("all")
}

enum class UserType(val type: String) {
    INSURANCE_AGENT("insurance_agent"),
    INSURANCE_CLIENT("insurance_client")
}

class InsuranceRequestBody (
    var userEmail: String,
    var propertyType: String= "",
    var amount: String,
    var policyStartDate: Date,
    var policyEndDate: Date,
    var address: AddressBody,
    var companyId: Long=1
)

class CompanyRequestBody(
    var companyName: String ="",
    var description: String = "",
    var imageURL: String="",
    var address: AddressBody
)

class SignUpRequestBody(
    var username: String,
    var password: String
)

class InsuranceClaimBody(
    var policyId: Long,
    var description: String = "",
    var claimDate: Date
)
