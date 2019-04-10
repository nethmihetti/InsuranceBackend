package com.inno.soramitsu.insurance.RESTserver.model

import javax.persistence.*
import javax.validation.constraints.NotBlank

/**
 * Created by nethmih on 10.04.19.
 */

@Entity
@Table(name = "address")
data class UserAddress (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "addressId")
        var addressId: Long = 0,

        @get: NotBlank
        @Column(name = "houseNum")
        var houseNum: String = "",

        @get: NotBlank
        @Column(name = "apartmentNum")
        var apartmentNum: String = "",

        @get: NotBlank
        @Column(name = "street")
        var street: String = "",

        @get: NotBlank
        @Column(name = "city")
        var city: String = "",

        @get: NotBlank
        @Column(name = "state")
        var state: String = "",

        @get: NotBlank
        @Column(name = "country")
        var country: String = ""
)