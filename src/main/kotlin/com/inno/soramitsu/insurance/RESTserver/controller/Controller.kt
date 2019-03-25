package com.inno.soramitsu.insurance.RESTserver.controller

import com.inno.soramitsu.insurance.RESTserver.model.Insurance
import com.inno.soramitsu.insurance.RESTserver.service.InsuranceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by nethmih on 17.03.19.
 */


@RestController
@RequestMapping("/insurance")
class Controller {

    @Autowired
    lateinit var insuranceService: InsuranceService

    @GetMapping("/boxProducts")
    fun getAllBoxProducts() : List<Insurance> {
        return insuranceService.getAllInsuranceBoxProducts()
    }
}