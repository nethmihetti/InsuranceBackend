package com.inno.soramitsu.insurance.RESTserver.dao

import com.inno.soramitsu.insurance.RESTserver.model.Agent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Created by nethmih on 16.05.19.
 */

@Repository

interface AgentRepository : JpaRepository<Agent, Long>