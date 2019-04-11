package com.inno.soramitsu.insurance.RESTserver.service.impl

import com.inno.soramitsu.insurance.RESTserver.dao.AddressRepository
import com.inno.soramitsu.insurance.RESTserver.dao.InsuranceRepository
import com.inno.soramitsu.insurance.RESTserver.dao.UserRepository
import com.inno.soramitsu.insurance.RESTserver.model.*
import com.inno.soramitsu.insurance.RESTserver.service.InsuranceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

/**
 * Created by nethmih on 17.03.19.
 */

@Service("insuranceService")
class InsuranceServiceImpl : InsuranceService {

    @Autowired
    lateinit var insuranceRepository: InsuranceRepository

    @Autowired
    lateinit var addressRepository: AddressRepository

    @Autowired
    lateinit var userRepository: UserRepository

    override fun getAllInsuranceBoxProducts(): List<Insurance> {

        return insuranceRepository.findAll()
    }

    override fun postNewUser(newUser: UserBody) {

        //inserting the address to the db
        postNewAddress(newUser.address)

        val lastAddress = addressRepository.findTopByOrderByAddressidDesc().addressid

        if(lastAddress > 0) {
            userRepository.postNewUser(newUser,lastAddress)
        }

    }

    override fun postNewAddress(newAddress: AddressBody) {
        addressRepository.postNewAddress(newAddress)
    }

    override fun insertNewInsuranceRequest(insuranceRequestBody: InsuranceRequestBody) {
        //get the userID
        System.out.println("here" + insuranceRequestBody.username )

        //val user = userRepository.findByUsername(insuranceRequestBody.username)
        val user = userRepository.findByUsername("'nethmih'")

        System.out.println(user.userId)

        if(user.userId > 0) {
            //insert the request
            insuranceRepository.insertNewInsuranceRequest(insuranceRequestBody, user.userId)
        } else {
            System.out.println("user not found")
        }


    }

}