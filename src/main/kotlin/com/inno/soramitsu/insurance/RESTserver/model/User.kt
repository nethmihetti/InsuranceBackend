package com.inno.soramitsu.insurance.RESTserver.model

import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotBlank

/**
 * Created by nethmih on 10.04.19.
 */

@Entity
@Table(name = "userData")
data class User (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "userid")
        var userId: Long = 0,

        @get: NotBlank
        @Column(name = "username")
        var username: String = "",

        @get: NotBlank
        @Column(name = "firstname")
        var firstName: String = "",

        @get: NotBlank
        @Column(name = "middlename")
        var middleName: String = "",

        @get: NotBlank
        @Column(name = "lastname")
        var lastName: String = "",

        @get: NotBlank
        @Column(name = "password")
        var password: String = "",

        @get: NotBlank
        @Column(name = "email")
        var email: String = "",

        @get: NotBlank
        @Column(name = "mobilenum")
        var mobileNum: String = "",

        @get: NotBlank
        @Column(name = "passportnum")
        var passportNum: String = "",

        @get: NotBlank
        @Column(name = "passportissuedby")
        var passportIssuedBy: String = "",

        @get: NotBlank
        @Column(name = "passportissueddate")
        var passportIssuedDate: LocalDate,

        @OneToOne
        var address: UserAddress
)