package com.inno.soramitsu.insurance.RESTserver.model

import javax.persistence.*

/**
 * Created by nethmih on 16.05.19.
 */

@Entity
@Table(name = "insurance_users", schema = "main")
data class InsuranceUsers (

        @Id
        @Column(name = "insurance_user_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var insuranceusersid: Long? = null,

        @Column(name = "username")
        var username: String = "",

        @Column(name = "password")
        var password: String = ""

)