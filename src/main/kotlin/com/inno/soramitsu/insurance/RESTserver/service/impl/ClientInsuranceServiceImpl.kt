package com.inno.soramitsu.insurance.RESTserver.service.impl

import com.inno.soramitsu.insurance.RESTserver.dao.*
import com.inno.soramitsu.insurance.RESTserver.model.*
import com.inno.soramitsu.insurance.RESTserver.service.ClientInsuranceService
import com.inno.soramitsu.insurance.RESTserver.util.ServerUtil
import com.inno.soramitsu.insurance.RESTserver.util.exception.ServerErrorCodes
import com.inno.soramitsu.insurance.RESTserver.util.exception.ServerErrorMessages
import com.inno.soramitsu.insurance.RESTserver.util.exception.ServerExceptions
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.ZoneId
import java.util.*
import javax.transaction.Transactional

/**
 * Created by nethmih on 16.05.19.
 */

@Service("clientInsuranceService")
@Transactional
class ClientInsuranceServiceImpl(private val insuranceRepository: InsuranceRepository,
                                 private val addressRepository: AddressRepository,
                                 private val companyRepository: CompanyRepository,
                                 private val clientRepository: ClientRepository,
                                 private val claimRepository: ClaimRepository) : ClientInsuranceService {

    override fun updateClientDetails(updateUser: UserBody): Client {

        val client = clientRepository.findByEmail(updateUser.email)

        client.first_name = updateUser.firstName
        client.middle_name = updateUser.middleName
        client.last_name = updateUser.lastName
        client.mobile_num = updateUser.mobileNum
        client.passport_num = updateUser.passportNum
        client.passport_issued_by = updateUser.passportIssuedBy
        client.passport_issued_date = updateUser.passportIssuedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

        return clientRepository.save(client)

    }

    override fun insertNewInsuranceRequest(insuranceRequestBody: InsuranceRequestBody): Insurance {

        ServerUtil.validateInsuranceRequestDates(insuranceRequestBody.policyStartDate, insuranceRequestBody.policyEndDate)

        val newInsuranceRequest: Insurance

        // find client by email
        val user = clientRepository.findByEmail(insuranceRequestBody.userEmail)

        if(user.user_id > 0) {

            val userAddress = insuranceRequestBody.address

            //check if the address exist already
            val addressCount = addressRepository.getAddressCount(userAddress)
            val addressSaved: UserAddress

            addressSaved = if(addressCount > 0) {
                addressRepository.getAddressDetails(userAddress)
            } else {
                //post the Address
                val address = UserAddress(ServerUtil.generateRandomId(), userAddress.houseNum, userAddress.apartmentNum, userAddress.street,
                        userAddress.city, userAddress.state, userAddress.country)

                addressRepository.save(address)
            }

            //find company by company Id
            val company = companyRepository.findByCompanyid(insuranceRequestBody.companyId)

            if (addressSaved.address_id > 0 && company.companyid > 0) {

                //insert new insurance request
                newInsuranceRequest = Insurance(ServerUtil.generateRandomId(), insuranceRequestBody.propertyType,
                        insuranceRequestBody.amount.toDouble(), ServerUtil.convertToLocalDate(insuranceRequestBody.policyStartDate),
                        ServerUtil.convertToLocalDate(insuranceRequestBody.policyEndDate),
                        ServerUtil.convertToLocalDate(Date.from(Instant.now())),
                        InsuranceStatusType.PENDING.type, addressSaved, user, company)

                return insuranceRepository.save(newInsuranceRequest)
            }

        }

        throw ServerExceptions(ServerErrorMessages.INVALID_PARAM_ERROR, ServerErrorCodes.TYPE_INVALID,
                "please make sure company exist")

    }

    override fun getInsuranceRequestsForClient(requestTO: RequestTO): Page<Insurance> {
        return insuranceRepository.findByClientEmailOrderByInsurancerequestidDesc(requestTO.email,
                ServerUtil.getPageSize(requestTO.page, requestTO.size))
    }

    override fun insertNewInsuranceClaim(claimBody: InsuranceClaimBody): InsuranceClaim {
        val insurancePolicy  = insuranceRepository.findByInsuranceRequestId(claimBody.policyId)

        if(insurancePolicy.insurancerequestid > 0) {

            val claim = InsuranceClaim(ServerUtil.generateRandomId(),
                    claimBody.description, ServerUtil.convertToLocalDate(claimBody.claimDate),
                    InsuranceStatusType.PENDING.type, insurancePolicy)

            return claimRepository.save(claim)
        }

        throw ServerExceptions(ServerErrorMessages.INVALID_PARAM_ERROR, ServerErrorCodes.TYPE_INVALID,
                "please make sure insurance policy exist")
    }

    override fun getInsuranceClaimsForClient(requestTO: RequestTO): Page<InsuranceClaim> {
        return claimRepository.findByInsuranceClientEmailOrderByClaimedDateDesc(requestTO.email,
                ServerUtil.getPageSize(requestTO.page, requestTO.size))
    }

    override fun getAllCompanyDetails(): List<Company> {
        return companyRepository.findAll()
    }
}
