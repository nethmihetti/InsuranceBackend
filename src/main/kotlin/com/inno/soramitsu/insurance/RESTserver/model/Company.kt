package com.inno.soramitsu.insurance.RESTserver.model

import javax.persistence.*

/**
 * Created by nethmih on 13.04.19.
 */


@Entity
@Table(name = "company_details", schema = "main")
data class Company (

        @Id
        @Column(name = "company_id")
        var companyid: Long = 0,

        @Column(name = "company_name")
        var companyname: String = "",

        @OneToOne
        @JoinColumn(name="address_id", nullable = false)
        var address: UserAddress

)
