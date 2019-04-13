package com.inno.soramitsu.insurance.RESTserver.service.impl

import com.inno.soramitsu.insurance.RESTserver.dao.AddressRepository
import com.inno.soramitsu.insurance.RESTserver.dao.CompanyRepository
import com.inno.soramitsu.insurance.RESTserver.dao.InsuranceRepository
import com.inno.soramitsu.insurance.RESTserver.dao.UserRepository
import com.inno.soramitsu.insurance.RESTserver.model.*
import com.inno.soramitsu.insurance.RESTserver.service.InsuranceService
import com.inno.soramitsu.insurance.RESTserver.util.ServerUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
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

        val user = User(ServerUtil.generateRandomId(), newUser.username, newUser.firstName,
                newUser.middleName, newUser.lastName, newUser.password, newUser.email, newUser.mobileNum,
                newUser.passportNum, newUser.passportIssuedBy, ServerUtil.convertToLocalDate(newUser.passportIssuedDate))

        userRepository.save(user)

    }

    override fun postNewAddress(userAddress: AddressBody) {
        val addressId = ServerUtil.generateRandomId()
        val address = UserAddress(addressId, userAddress.houseNum, userAddress.apartmentNum, userAddress.street,
                userAddress.city, userAddress.state, userAddress.country)

        addressRepository.save(address)
    }

    override fun insertNewInsuranceRequest(insuranceRequestBody: InsuranceRequestBody) {
        //post the Address
        val userAddress = insuranceRequestBody.address
        val addressId = ServerUtil.generateRandomId()
        val address = UserAddress(addressId, userAddress.houseNum, userAddress.apartmentNum, userAddress.street,
                userAddress.city, userAddress.state, userAddress.country)

       // addressRepository.postNewAddress(address)
        val addressSaved = addressRepository.save(address)


        // find user by username
        val user = userRepository.findByUsername(insuranceRequestBody.username)

        //find company by company Id
        val company = companyRepository.findByCompanyid(insuranceRequestBody.companyId)

        if(addressSaved.address_id > 0 && user.user_id > 0 && company.companyid>0) {

            //insert new insurance request
            val newInsuranceRequest = Insurance(ServerUtil.generateRandomId(), insuranceRequestBody.propertyType,
                    insuranceRequestBody.amount, ServerUtil.convertToLocalDate(insuranceRequestBody.policyStartDate),
                    ServerUtil.convertToLocalDate(insuranceRequestBody.policyEndDate),
                    ServerUtil.convertToLocalDate(insuranceRequestBody.policyCreatedDate),
                    insuranceRequestBody.status, addressSaved, user, company)

            insuranceRepository.save(newInsuranceRequest)
        } else {
            System.out.println("company, user or address does not exist")
        }

    }

    override fun getInsuranceRequestsForCompany(companyId: Long): List<Insurance> {
        return insuranceRepository.findByCompanyCompanyid(companyId)
    }

    override fun updateInsuranceStatus(insuranceId: Long, status: InsuranceStatusType) {
        insuranceRepository.updateInsuranceStatus(insuranceId, status.type)
    }

    override fun insertNewCompany(companyRequestBody: CompanyRequestBody) {

        //post the Address
        val userAddress = companyRequestBody.address
        val addressId = ServerUtil.generateRandomId()
        val address = UserAddress(addressId, userAddress.houseNum, userAddress.apartmentNum, userAddress.street,
                userAddress.city, userAddress.state, userAddress.country)

        val addressSaved = addressRepository.save(address)

        if(addressSaved.address_id > 0) {
            //post the new company
            val newCompany = Company(ServerUtil.generateRandomId(), companyRequestBody.CompanyName, addressSaved)
            companyRepository.save(newCompany)
        }


    }

}