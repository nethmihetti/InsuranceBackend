package com.inno.soramitsu.insurance.RESTserver.service.impl

import com.inno.soramitsu.insurance.RESTserver.controller.AddressBody
import com.inno.soramitsu.insurance.RESTserver.controller.UserBodyNew
import com.inno.soramitsu.insurance.RESTserver.dao.AddressRepository
import com.inno.soramitsu.insurance.RESTserver.dao.InsuranceRepository
import com.inno.soramitsu.insurance.RESTserver.dao.UserRepository
import com.inno.soramitsu.insurance.RESTserver.model.Insurance
import com.inno.soramitsu.insurance.RESTserver.model.User
import com.inno.soramitsu.insurance.RESTserver.model.UserAddress
import com.inno.soramitsu.insurance.RESTserver.model.UserBody
import com.inno.soramitsu.insurance.RESTserver.service.InsuranceService
import org.apache.tomcat.jni.Address
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

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

    override fun postNewUser(newUser: UserBodyNew) {

      //  var address: UserAddress = newUser.address
     //   var user = User(newUser.email)
        System.out.println(newUser.passportIssuedDate)
        userRepository.postNewUser(newUser, 2)


    }

    override fun postNewAddress(newAddress: AddressBody) {
        addressRepository.postNewAddress(newAddress)
    }

}