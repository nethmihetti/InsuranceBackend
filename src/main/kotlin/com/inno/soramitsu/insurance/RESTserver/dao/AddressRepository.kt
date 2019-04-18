package com.inno.soramitsu.insurance.RESTserver.dao

import com.inno.soramitsu.insurance.RESTserver.model.AddressBody
import com.inno.soramitsu.insurance.RESTserver.model.UserAddress
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

/**
 * Created by nethmih on 10.04.19.
 */
@Repository

interface AddressRepository : JpaRepository<UserAddress, Long> {

    @Transactional
    @Query("SELECT * FROM address ad WHERE ad.house_num =:#{#address.houseNum} AND ad.apartment_num = :#{#address.apartmentNum} AND ad.street=:#{#address.street} AND ad.city=:#{#address.city} AND ad.state=:#{#address.state} AND ad.country=:#{#address.country}", nativeQuery = true)
    fun getAddressDetails(@Param("address") address: AddressBody) : UserAddress

    @Transactional
    @Query("SELECT COUNT(ad) FROM address ad WHERE ad.house_num =:#{#address.houseNum} AND ad.apartment_num = :#{#address.apartmentNum} AND ad.street=:#{#address.street} AND ad.city=:#{#address.city} AND ad.state=:#{#address.state} AND ad.country=:#{#address.country}", nativeQuery = true)
    fun getAddressCount(@Param("address") address: AddressBody) : Int

}