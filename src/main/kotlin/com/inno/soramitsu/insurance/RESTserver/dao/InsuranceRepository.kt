package com.inno.soramitsu.insurance.RESTserver.dao

import com.inno.soramitsu.insurance.RESTserver.model.Insurance
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Created by nethmih on 17.03.19.
 */

@Repository
interface InsuranceRepository : JpaRepository<Insurance, Long>