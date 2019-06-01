package com.inno.soramitsu.insurance.RESTserver.service

import com.inno.soramitsu.insurance.RESTserver.model.*
import org.springframework.data.domain.Page

/**
 * Created by nethmih on 16.05.19.
 */

interface ClientInsuranceService {

    fun updateClientDetails(updateUser: UserBody): Client

    fun insertNewInsuranceRequest(insuranceRequestBody: InsuranceRequestBody): Insurance

    fun getInsuranceRequestsForClient(requestTO: RequestTO): Page<Insurance>
}