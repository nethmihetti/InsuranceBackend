package com.inno.soramitsu.insurance.RESTserver.model

import java.math.BigInteger
import java.time.LocalDate
import javax.persistence.*

/**
 * Created by nethmih on 17.03.19.
 */


@Entity
@Table(name = "insurance_request", schema = "main")
data class Insurance (

        @Id
        @Column(name = "insurance_request_id")
        var insurancerequestid: BigInteger,

        @Column(name = "property_type")
        var propertytype: String = "",

        @Column(name = "amount")
        var amount: Double = 0.00,

        @Column(name = "policy_start_date")
        var policystartdate: LocalDate,

        @Column(name = "policy_end_date")
        var policyenddate: LocalDate,

        @Column(name = "policy_created_date")
        var policycreatedcate: LocalDate,

        @Column(name = "status")
        var status: String,

        @ManyToOne
        @JoinColumn(name="address_id", nullable = false)
        var address: UserAddress,

        @ManyToOne
        @JoinColumn(name="user_id", nullable = false)
        var user: User,

        @OneToOne
        @JoinColumn(name="company_id", nullable = false)
        var company: Company
)
