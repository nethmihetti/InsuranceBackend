package com.inno.soramitsu.insurance.RESTserver.dao

import com.inno.soramitsu.insurance.RESTserver.model.Agent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

/**
 * Created by nethmih on 16.05.19.
 */

@Repository
interface AgentRepository : JpaRepository<Agent, Long> {

    @Transactional
    fun findByUsername(@Param("username") username: String): Agent
}