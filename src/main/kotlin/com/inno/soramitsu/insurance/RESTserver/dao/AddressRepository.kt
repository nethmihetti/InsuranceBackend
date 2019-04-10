package com.inno.soramitsu.insurance.RESTserver.dao

import com.inno.soramitsu.insurance.RESTserver.controller.AddressBody
import com.inno.soramitsu.insurance.RESTserver.model.UserAddress
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

/**
 * Created by nethmih on 10.04.19.
 */
@Repository

interface AddressRepository : JpaRepository<UserAddress, Long> {
    @Modifying
    @Query(value = "insert into address(houseNum, apartmentNum, street, city, state, country)" +
            "VALUES(:#{#newAddress.houseNum},:#{#newAddress.apartmentNum}, :#{#newAddress.street}, :#{#newAddress.city}, :#{#newAddress.state}, :#{#newAddress.country})",
            nativeQuery = true)

    @Transactional
    fun postNewAddress(@Param("newAddress") newAddress: AddressBody)
}