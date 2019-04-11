package com.inno.soramitsu.insurance.RESTserver.dao

import com.inno.soramitsu.insurance.RESTserver.model.Insurance
import com.inno.soramitsu.insurance.RESTserver.model.InsuranceRequestBody
import com.inno.soramitsu.insurance.RESTserver.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

/**
 * Created by nethmih on 17.03.19.
 */

@Repository
interface InsuranceRepository : JpaRepository<Insurance, Long> {

    @Modifying
    @Query(value = "insert into insurancerequest(userid, propertytype, amount, policystartdate, policyenddate, policycreatedcate, status)" +
            "VALUES(:#{#userId}, :#{#newRequest.propertyType}, :#{#newRequest.amount}, :#{#newRequest.policyStartDate}, :#{#newRequest.policyEndDate}, :#{#newRequest.policyCreatedDate}, :#{#newRequest.status})",
            nativeQuery = true)

    @Transactional
    fun insertNewInsuranceRequest(@Param("newRequest") newRequest: InsuranceRequestBody, @Param("userId") userId: Long)

}