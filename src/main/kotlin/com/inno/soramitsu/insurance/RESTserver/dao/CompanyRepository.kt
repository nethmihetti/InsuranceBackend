package com.inno.soramitsu.insurance.RESTserver.dao

import com.inno.soramitsu.insurance.RESTserver.model.Company
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

/**
 * Created by nethmih on 13.04.19.
 */

@Repository

interface CompanyRepository : JpaRepository<Company, Long> {
    @Transactional
    fun findByCompanyid(@Param("companyId") companyId: Long): Company
}