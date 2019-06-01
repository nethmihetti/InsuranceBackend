package com.inno.soramitsu.insurance.RESTserver.dao

import com.inno.soramitsu.insurance.RESTserver.model.InsuranceUsers
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

/**
 * Created by nethmih on 30.05.19.
 */
@Repository
interface InsuranceUsersRepository : JpaRepository<InsuranceUsers, Long> {

    @Transactional
    fun findByUsername(@Param("username") username: String): InsuranceUsers
}