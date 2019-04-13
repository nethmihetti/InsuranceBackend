package com.inno.soramitsu.insurance.RESTserver.dao

import com.inno.soramitsu.insurance.RESTserver.model.Insurance
import com.inno.soramitsu.insurance.RESTserver.model.InsuranceRequestBody
import com.inno.soramitsu.insurance.RESTserver.model.InsuranceStatusType
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
    @Query(value = "insert into insurance_request(insurance_request_id, property_type, amount, policy_start_date, policy_end_date, policy_create_dcate, status, address_id, user_id)" +
            "VALUES(:#{#newRequest.insurancerequestid}, :#{#newRequest.propertyType}, :#{#newRequest.amount}, :#{#newRequest.policyStartDate}, :#{#newRequest.policyEndDate}, :#{#newRequest.policyCreatedDate}, :#{#newRequest.status}, :#{#newRequest.addressid}, :#{#newRequest.userid})",
            nativeQuery = true)

    @Transactional
    fun insertNewInsuranceRequest(@Param("newRequest") newRequest: Insurance)

    @Transactional
    fun findByCompanyCompanyid(@Param("companyId") companyId: Long): List<Insurance>

    @Transactional
    @Modifying
    @Query(value = "update insurance_request set status = :#{#status} where insurance_request_id = :#{#insuranceId}", nativeQuery = true)
    fun updateInsuranceStatus(@Param("insuranceId") insuranceId: Long, @Param("status") status: String)

}