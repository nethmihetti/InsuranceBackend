package com.inno.soramitsu.insurance.RESTserver.dao

import com.inno.soramitsu.insurance.RESTserver.model.User
import com.inno.soramitsu.insurance.RESTserver.model.UserBody
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

interface UserRepository : JpaRepository<User, Long> {
    @Modifying
    @Query(value = "insert into userData (username, firstName, middleName, lastName, password, email, mobileNum, passportNum, passportIssuedBy, passportIssuedDate, addressId)" +
            "VALUES(:#{#newUser.username},:#{#newUser.firstName}, :#{#newUser.middleName}, :#{#newUser.lastName}, :#{#newUser.password}, :#{#newUser.email}, :#{#newUser.mobileNum}, :#{#newUser.passportNum}, :#{#newUser.passportIssuedBy}, :#{#newUser.passportIssuedDate}, :#{#addressId})",
            nativeQuery = true)

    @Transactional
    fun postNewUser(@Param("newUser") newUser: UserBody, @Param("addressId") addressId: Long)

    @Transactional
    fun findByUsername(@Param("username") username: String): User
}



