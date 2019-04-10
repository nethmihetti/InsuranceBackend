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
        @Column(name = "userId")
        var userId: Long = 0,

        @get: NotBlank
        @Column(name = "username")
        var username: String = "",

        @get: NotBlank
        @Column(name = "firstName")
        var firstName: String = "",

        @get: NotBlank
        @Column(name = "middleName")
        var middleName: String = "",

        @get: NotBlank
        @Column(name = "lastName")
        var lastName: String = "",

        @get: NotBlank
        @Column(name = "password")
        var password: String = "",

        @get: NotBlank
        @Column(name = "email")
        var email: String = "",

        @get: NotBlank
        @Column(name = "mobileNum")
        var mobileNum: String = "",

        @get: NotBlank
        @Column(name = "passportNum")
        var passportNum: String = "",

        @get: NotBlank
        @Column(name = "passportIssuedBy")
        var passportIssuedBy: String = "",

        @get: NotBlank
        @Column(name = "passportIssuedDate")
        var passportIssuedDate: LocalDate,

        @OneToOne
        var address: UserAddress
)