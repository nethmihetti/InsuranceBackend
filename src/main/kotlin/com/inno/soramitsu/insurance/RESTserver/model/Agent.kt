package com.inno.soramitsu.insurance.RESTserver.model

import javax.persistence.*
import javax.validation.constraints.NotBlank

/**
 * Created by nethmih on 30.05.19.
 */

@Entity
@Table(name = "insurance_agents", schema = "main")
data class Agent (

        @Id
        @Column(name = "agent_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var agent_id: Long,

        @get: NotBlank
        @Column(name = "first_name")
        var first_name: String = "",

        @get: NotBlank
        @Column(name = "middle_name")
        var middle_name: String = "",

        @get: NotBlank
        @Column(name = "last_name")
        var last_name: String = "",

        @OneToOne
        @JoinColumn(name="insurance_user_id", nullable = false)
        var insuranceuser: InsuranceUsers,

        @OneToOne
        @JoinColumn(name="company_id", nullable = false)
        var company: Company

)