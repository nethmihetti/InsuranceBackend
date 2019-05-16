package com.inno.soramitsu.insurance.RESTserver.service.impl

import com.inno.soramitsu.insurance.RESTserver.dao.*
import com.inno.soramitsu.insurance.RESTserver.model.*
import com.inno.soramitsu.insurance.RESTserver.service.AgentInsuranceService
import com.inno.soramitsu.insurance.RESTserver.util.ServerUtil
import com.inno.soramitsu.insurance.RESTserver.util.exception.ServerErrorCodes
import com.inno.soramitsu.insurance.RESTserver.util.exception.ServerErrorMessages
import com.inno.soramitsu.insurance.RESTserver.util.exception.ServerExceptions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*
import javax.transaction.Transactional

/**
 * Created by nethmih on 17.03.19.
 */

@Service("agentInsuranceService")
@Transactional
class AgentInsuranceServiceImpl : AgentInsuranceService {

    @Autowired
    lateinit var insuranceRepository: InsuranceRepository

    @Autowired
    lateinit var addressRepository: AddressRepository

    @Autowired
    lateinit var companyRepository: CompanyRepository

    @Autowired
    lateinit var agentRepository: AgentRepository


    override fun getInsuranceRequestsForCompany(companyId: Long, status: InsuranceStatusQueryType): List<Insurance> {
        if(!status.type.equals("all")) {
            return insuranceRepository.findByCompanyCompanyidAndStatus(companyId, status.type)
        }
        return insuranceRepository.findByCompanyCompanyid(companyId)
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

    override fun postNewAgent(agentSignURequestBody: AgentSignURequestBody) : Agent {

        val company: Company = companyRepository.findByCompanyid(agentSignURequestBody.companyId)

        val newAgent = Agent(ServerUtil.generateRandomId(), agentSignURequestBody.email, agentSignURequestBody.password, company)

        return agentRepository.save(newAgent)
    }

}