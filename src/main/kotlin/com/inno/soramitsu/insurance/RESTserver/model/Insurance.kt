package com.inno.soramitsu.insurance.RESTserver.model

import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotBlank

/**
 * Created by nethmih on 17.03.19.
 */


@Entity
@Table(name = "insuranceboxproducts")
data class Insurance (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Long = 0,

        @get: NotBlank
        @Column(name = "title")
        var title: String = "",

        @get: NotBlank
        @Column(name = "content")
        var content: String = "",

        @get: NotBlank
        @Column(name = "date_prod")
        var date_prod: LocalDate
)