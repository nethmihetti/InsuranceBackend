package com.inno.soramitsu.insurance.RESTserver.service.impl

import com.inno.soramitsu.insurance.RESTserver.dao.*
import com.inno.soramitsu.insurance.RESTserver.model.*
import com.inno.soramitsu.insurance.RESTserver.service.AgentInsuranceService
import com.inno.soramitsu.insurance.RESTserver.util.ServerUtil
import com.inno.soramitsu.insurance.RESTserver.util.exception.ServerErrorCodes
import com.inno.soramitsu.insurance.RESTserver.util.exception.ServerErrorMessages
import com.inno.soramitsu.insurance.RESTserver.util.exception.ServerExceptions
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import javax.transaction.Transactional

/**
 * Created by nethmih on 17.03.19.
 */

@Service("agentInsuranceService")
@Transactional
class AgentInsuranceServiceImpl(private val insuranceRepository: InsuranceRepository,
                                private val addressRepository: AddressRepository,
                                private val companyRepository: CompanyRepository) : AgentInsuranceService {

    override fun getInsuranceRequestsForCompany(requestTO: RequestTO): Page<Insurance> {
        if(!requestTO.status.type.equals("all")) {
            return insuranceRepository.findByCompanyCompanyidAndStatusOrderByInsurancerequestidDesc(
                    requestTO.companyId, requestTO.status.type, ServerUtil.getPageSize(requestTO.page, requestTO.size))
        }
        return insuranceRepository.findByCompanyCompanyidOrderByInsurancerequestidDesc(requestTO.companyId,
                ServerUtil.getPageSize(requestTO.page, requestTO.size))
    }

    override fun updateInsuranceStatus(insuranceId: Long, status: InsuranceStatusType): Insurance {
        insuranceRepository.updateInsuranceStatus(insuranceId , status.type)

        return insuranceRepository.findByInsuranceRequestId(insuranceId)
    }

    override fun insertNewCompany(companyRequestBody: CompanyRequestBody): Company {

        //post the Address
        val userAddress = companyRequestBody.address

        val address = UserAddress(ServerUtil.generateRandomId(), userAddress.houseNum, userAddress.apartmentNum, userAddress.street,
                userAddress.city, userAddress.state, userAddress.country)

        val addressSaved = addressRepository.save(address)

        if(addressSaved.address_id > 0) {
            //post the new company
            val newCompany = Company(ServerUtil.generateRandomId(), companyRequestBody.companyName, addressSaved)
            return companyRepository.save(newCompany)
        }

        throw ServerExceptions(ServerErrorMessages.NULL_RESPONSE_FROM_DATABASE, ServerErrorCodes.TYPE_INVALID,
                "error inserting the company address or company details")
    }

}