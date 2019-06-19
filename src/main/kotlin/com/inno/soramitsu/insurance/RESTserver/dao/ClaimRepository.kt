package com.inno.soramitsu.insurance.RESTserver.dao

import com.inno.soramitsu.insurance.RESTserver.model.InsuranceClaim
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
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

    fun findByInsuranceCompanyCompanyidOrderByClaimedDateDesc(@Param("companyid") companyId: Long,
                                                              @Param("page") page: Pageable): Page<InsuranceClaim>

}