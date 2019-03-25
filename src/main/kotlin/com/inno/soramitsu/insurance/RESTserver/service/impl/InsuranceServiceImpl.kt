package com.inno.soramitsu.insurance.RESTserver.service.impl

import com.inno.soramitsu.insurance.RESTserver.dao.InsuranceRepository
import com.inno.soramitsu.insurance.RESTserver.model.Insurance
import com.inno.soramitsu.insurance.RESTserver.service.InsuranceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by nethmih on 17.03.19.
 */

@Service("insuranceService")
class InsuranceServiceImpl : InsuranceService {

    @Autowired
    lateinit var insuranceRepository: InsuranceRepository

    override fun getAllInsuranceBoxProducts(): List<Insurance> {
        return insuranceRepository.findAll()

       /* val insurance = Insurance()

        insurance.id = 1
        insurance.content = "some content"
        insurance.title = "box product"

        return arrayOf(insurance).asList()*/

    }

}