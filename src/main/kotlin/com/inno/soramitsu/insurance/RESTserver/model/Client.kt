package com.inno.soramitsu.insurance.RESTserver.model

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotBlank

/**
 * Created by nethmih on 10.04.19.
 */

@Entity
@Table(name = "user_data", schema = "main")
@NoArgsConstructor
@AllArgsConstructor
data class Client (

        @Id
        @JsonIgnore
        @Column(name = "user_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var user_id: Long,

        @get: NotBlank
        @Column(name = "first_name")
        var first_name: String = "",

        @get: NotBlank
        @Column(name = "middle_name")
        var middle_name: String = "",

        @get: NotBlank
        @Column(name = "last_name")
        var last_name: String = "",

        @get: NotBlank
        @Column(name = "email")
        var email: String = "",

        @get: NotBlank
        @Column(name = "mobile_num")
        var mobile_num: String = "",

        @get: NotBlank
        @Column(name = "passport_num")
        var passport_num: String = "",

        @get: NotBlank
        @Column(name = "passport_issued_by")
        var passport_issued_by: String = "",

        @Column(name = "passport_issued_date")
        var passport_issued_date: LocalDate,

        @OneToOne
        @JoinColumn(name="insurance_user_id", nullable = false)
        var insuranceuser: InsuranceUsers

)
