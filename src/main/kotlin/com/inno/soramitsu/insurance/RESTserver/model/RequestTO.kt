package com.inno.soramitsu.insurance.RESTserver.model

/**
 * Created by nethmih on 29.05.19.
 */

object RequestTO {
    var companyId: Long = 1L
    lateinit var status: InsuranceStatusQueryType
    var email: String = ""
    var page: Int? = null
    var size: Int? = null
}