package com.inno.soramitsu.insurance.RESTserver.service.impl

import com.inno.soramitsu.insurance.RESTserver.dao.AddressRepository
import com.inno.soramitsu.insurance.RESTserver.dao.CompanyRepository
import com.inno.soramitsu.insurance.RESTserver.dao.InsuranceRepository
import com.inno.soramitsu.insurance.RESTserver.dao.UserRepository
import com.inno.soramitsu.insurance.RESTserver.model.*
import com.inno.soramitsu.insurance.RESTserver.service.InsuranceService
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

@Service("insuranceService")
@Transactional
class InsuranceServiceImpl : InsuranceService {

    @Autowired
    lateinit var insuranceRepository: InsuranceRepository

    @Autowired
    lateinit var addressRepository: AddressRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var companyRepository: CompanyRepository

    override fun getAllInsuranceBoxProducts(): List<Insurance> {

        return insuranceRepository.findAll()
    }

    @Transactional
    override fun postNewUser(newUser: UserBody): User {

        val user = User(ServerUtil.generateRandomId(), newUser.firstName,
                newUser.middleName, newUser.lastName, newUser.email, newUser.mobileNum,
                newUser.passportNum, newUser.passportIssuedBy, ServerUtil.convertToLocalDate(newUser.passportIssuedDate))

        return userRepository.save(user)

    }

    override fun insertNewInsuranceRequest(insuranceRequestBody: InsuranceRequestBody): Insurance {

        val newInsuranceRequest: Insurance

        // find user by email
        val user = userRepository.findByEmail(insuranceRequestBody.userEmail)

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

    override fun getInsuranceRequestsForCompany(companyId: Long, status: InsuranceStatusQueryType): List<Insurance> {
        if(!status.type.equals("all")) {
            return insuranceRepository.findByCompanyCompanyidAndStatus(companyId, status.type)
        }
        val ins =insuranceRepository.findByCompanyCompanyidOrderByInsurancerequestidDesc(companyId)

        for (item in ins)
            println(item.insurancerequestid)

        return ins
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

    override fun getInsuranceRequestsForClient(email: String): List<Insurance> {
        return insuranceRepository.findByUserEmail(email)
    }

}