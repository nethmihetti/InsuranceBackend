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
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var companyid: Long,

        @Column(name = "company_name")
        var companyname: String = "",

        @Column(name = "description")
        var description: String = "",

        @Column(name = "image_url")
        var image_url: String = "",

        @OneToOne
        @JoinColumn(name="address_id", nullable = false)
        var address: UserAddress

)
