package com.inno.soramitsu.insurance.RESTserver.dao

import com.inno.soramitsu.insurance.RESTserver.model.InsuranceClaim
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

/**
 * Created by nethmih on 17.06.19.
 */

@Repository
interface ClaimRepository : JpaRepository<InsuranceClaim, Long> {
    @Transactional
    fun findByInsuranceCompanyCompanyidAndStatusInOrderByClaimedDateDesc(@Param("companyid") companyId: Long,
                                                                       @Param("status") status: List<String>,
                                                                       @Param("page") page: Pageable): Page<InsuranceClaim>

    @Transactional
    fun findByInsuranceCompanyCompanyidOrderByClaimedDateDesc(@Param("companyid") companyId: Long,
                                                              @Param("page") page: Pageable): Page<InsuranceClaim>

    @Transactional
    @Modifying
    @Query(value = "update insurance_claims set status = :#{#status} where claim_id = :#{#claimId}", nativeQuery = true)
    fun updateInsuranceClaimStatus(@Param("claimId") claimId: Long, @Param("status") status: String)

    @Transactional
    fun findByClaimid(@Param("claimid") claimid: Long): InsuranceClaim

    @Transactional
    fun findByInsuranceClientEmailOrderByClaimedDateDesc(@Param("email") email: String,
                                                         @Param("page") page: Pageable): Page<InsuranceClaim>

}
