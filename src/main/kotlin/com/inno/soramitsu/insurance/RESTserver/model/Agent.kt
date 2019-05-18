package com.inno.soramitsu.insurance.RESTserver.model

import javax.persistence.*

/**
 * Created by nethmih on 16.05.19.
 */

@Entity
@Table(name = "insurance_agents", schema = "main")
data class Agent (

        @Id
        @Column(name = "agent_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var agentid: Long,

        @Column(name = "username")
        var username: String = "",

        @Column(name = "password")
        var password: String = "",

        @OneToOne
        @JoinColumn(name="company_id", nullable = false)
        var company: Company

)