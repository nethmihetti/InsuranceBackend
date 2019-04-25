package com.inno.soramitsu.insurance.RESTserver.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.NotBlank

/**
 * Created by nethmih on 10.04.19.
 */

@Entity
@Table(name = "address", schema = "main")
data class UserAddress (

        @Id
        @JsonIgnore
        @Column(name = "address_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var address_id: Long,

        @get: NotBlank
        @Column(name = "house_num")
        var house_num: String = "",

        @get: NotBlank
        @Column(name = "apartment_num")
        var apartment_num: String = "",

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