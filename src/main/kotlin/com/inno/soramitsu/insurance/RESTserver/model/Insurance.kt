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

     /*   @OneToOne(mappedBy = "insuranceRequest")
        val userId: User,

        @OneToOne(mappedBy = "insuranceRequest")
        val addressId: UserAddress,*/

        @get: NotBlank
        @Column(name = "propertyType")
        var propertyType: String = "",

        @get: NotBlank
        @Column(name = "amount")
        var amount: Double = 0.00,

        @get: NotBlank
        @Column(name = "policyStartDate")
        var policyStartDate: LocalDate,

        @get: NotBlank
        @Column(name = "policyEndDate")
        var policyEndDate: LocalDate,

        @get: NotBlank
        @Column(name = "policyCreatedDate")
        var policyCreatedDate: LocalDate
)