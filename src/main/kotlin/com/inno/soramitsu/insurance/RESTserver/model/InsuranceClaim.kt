package com.inno.soramitsu.insurance.RESTserver.model

import java.time.LocalDate
import javax.persistence.*

/**
 * Created by nethmih on 17.06.19.
 */

@Entity
@Table(name = "insurance_claims", schema = "main")
data class InsuranceClaim (

        @Id
        @Column(name = "claim_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var claimid: Long? = null,

        @Column(name = "description")
        var description: String = "",

        @Column(name = "claimed_date")
        var claimedDate: LocalDate,

        @Column(name = "status")
        var status: String = "",

        @ManyToOne
        @JoinColumn(name="insurance_request_id", nullable = false)
        var insurance: Insurance

)