package com.inno.soramitsu.insurance.RESTserver.dao

import com.inno.soramitsu.insurance.RESTserver.model.User
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
    /*@Modifying
    @Query(value = "insert into user_data (user_id, username, first_name, middle_name, last_name, password, email, mobile_num, passport_num, passport_issued_by, passport_issued_date)" +
            "VALUES(:#{#newUser.user_id}, :#{#newUser.username}, :#{#newUser.first_name}, :#{#newUser.middle_name}, :#{#newUser.last_name}, :#{#newUser.password}, :#{#newUser.email}, :#{#newUser.mobile_num}, :#{#newUser.passport_num}, :#{#newUser.passport_issued_by}, :#{#newUser.passport_issued_date})",
            nativeQuery = true)

    @Transactional
    fun postNewUser(@Param("newUser") newUser: User)*/

    @Transactional
    fun findByUsername(@Param("username") username: String): User
}



