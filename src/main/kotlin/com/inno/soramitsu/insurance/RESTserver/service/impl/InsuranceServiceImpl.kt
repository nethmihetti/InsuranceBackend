package com.inno.soramitsu.insurance.RESTserver.service.impl

import com.inno.soramitsu.insurance.RESTserver.dao.InsuranceRepository
import com.inno.soramitsu.insurance.RESTserver.model.Insurance
import com.inno.soramitsu.insurance.RESTserver.service.InsuranceService
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

    override fun getAllInsuranceBoxProducts(): List<Insurance> {

        var envVar: String = System.getenv("DEMO_GREETING") ?: "default_value"

        System.out.println(envVar)
       // return insuranceRepository.findAll()

        val insurance = Insurance(12,"some title", envVar, LocalDate.parse("2018-12-31"))


        return arrayOf(insurance).asList()

    }

}