package com.inno.soramitsu.insurance.RESTserver.model

import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotBlank

/**
 * Created by nethmih on 17.03.19.
 */


@Entity
@Table(name = "insuranceRequest")
data class Insurance (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "insuranceRequestId")
        var insuranceRequestId: Long = 0,

        @OneToOne
        val userId: User,

        /*  @OneToOne(mappedBy = "insuranceRequest")
          val addressId: UserAddress,*/

        @get: NotBlank
        @Column(name = "propertytype")
        var propertytype: String = "",

        @get: NotBlank
        @Column(name = "amount")
        var amount: Double = 0.00,

        @get: NotBlank
        @Column(name = "policystartdate")
        var policystartdate: LocalDate,

        @get: NotBlank
        @Column(name = "policyenddate")
        var policyenddate: LocalDate,

        @get: NotBlank
        @Column(name = "policycreatedcate")
        var policycreatedcate: LocalDate,

        @get: NotBlank
        @Column(name = "status")
        var status: String=""
)