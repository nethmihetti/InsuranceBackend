package com.inno.soramitsu.insurance.RESTserver.service.impl

import com.inno.soramitsu.insurance.RESTserver.dao.AddressRepository
import com.inno.soramitsu.insurance.RESTserver.dao.CompanyRepository
import com.inno.soramitsu.insurance.RESTserver.dao.InsuranceRepository
import com.inno.soramitsu.insurance.RESTserver.dao.UserRepository
import com.inno.soramitsu.insurance.RESTserver.model.*
import com.inno.soramitsu.insurance.RESTserver.service.ClientInsuranceService
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
 * Created by nethmih on 16.05.19.
 */

@Service("clientInsuranceService")
@Transactional
class ClientInsuranceServiceImpl : ClientInsuranceService {

    @Autowired
    lateinit var insuranceRepository: InsuranceRepository

    @Autowired
    lateinit var addressRepository: AddressRepository

    @Autowired
    lateinit var companyRepository: CompanyRepository

    @Autowired
    lateinit var userRepository: UserRepository


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

    override fun getInsuranceRequestsForClient(email: String): List<Insurance> {
        return insuranceRepository.findByUserEmail(email)
    }
}