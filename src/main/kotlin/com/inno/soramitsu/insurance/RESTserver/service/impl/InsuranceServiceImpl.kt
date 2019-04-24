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
import java.math.BigInteger
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
    override fun postNewUser(newUser: UserBody) {

        val user = User(ServerUtil.generateRandomId(), newUser.firstName,
                newUser.middleName, newUser.lastName, newUser.email, newUser.mobileNum,
                newUser.passportNum, newUser.passportIssuedBy, ServerUtil.convertToLocalDate(newUser.passportIssuedDate))

        userRepository.save(user)

    }

    override fun insertNewInsuranceRequest(insuranceRequestBody: InsuranceRequestBody): Insurance {

        val newInsuranceRequest: Insurance

        val addressId = ServerUtil.generateRandomId()
        val insuranceRequestId = ServerUtil.generateRandomId()

        // find user by email
        val user = userRepository.findByEmail(insuranceRequestBody.userEmail)

        if(user.user_id > BigInteger.ZERO) {

            val userAddress = insuranceRequestBody.address

            //check if the address exist already
            val addressCount = addressRepository.getAddressCount(userAddress)
            val addressSaved: UserAddress

            addressSaved = if(addressCount > 0) {
                addressRepository.getAddressDetails(userAddress)
            } else {
                //post the Address
                val address = UserAddress(addressId, userAddress.houseNum, userAddress.apartmentNum, userAddress.street,
                        userAddress.city, userAddress.state, userAddress.country)

                addressRepository.save(address)
            }

            //find company by company Id
            val company = companyRepository.findByCompanyid(insuranceRequestBody.companyId)

            if (addressSaved.address_id > BigInteger.ZERO && company.companyid > BigInteger.ZERO) {

                //insert new insurance request
                newInsuranceRequest = Insurance(insuranceRequestId, insuranceRequestBody.propertyType,
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
            return insuranceRepository.findByCompanyCompanyidAndStatus(BigInteger.valueOf(companyId), status.type)
        }
        return insuranceRepository.findByCompanyCompanyid(BigInteger.valueOf(companyId))
    }

    override fun updateInsuranceStatus(insuranceId: Long, status: InsuranceStatusType): Insurance {
        insuranceRepository.updateInsuranceStatus(BigInteger.valueOf(insuranceId) , status.type)

        return insuranceRepository.findByInsuranceRequestId(BigInteger.valueOf(insuranceId))
    }

    override fun insertNewCompany(companyRequestBody: CompanyRequestBody) {

        //post the Address
        val userAddress = companyRequestBody.address

        val addressId = ServerUtil.generateRandomId()
        val companyId = ServerUtil.generateRandomId()

        val address = UserAddress(addressId, userAddress.houseNum, userAddress.apartmentNum, userAddress.street,
                userAddress.city, userAddress.state, userAddress.country)

        val addressSaved = addressRepository.save(address)

        if(addressSaved.address_id > BigInteger.ZERO) {
            //post the new company
            val newCompany = Company(companyId, companyRequestBody.CompanyName, addressSaved)
            companyRepository.save(newCompany)
        }


    }

    override fun getInsuranceRequestsForClient(email: String): List<Insurance> {
        return insuranceRepository.findByUserEmail(email)
    }

}