package com.inno.soramitsu.insurance.RESTserver.util

import java.lang.Long.MAX_VALUE
import java.math.BigInteger
import java.time.LocalDate
import java.time.ZoneId
import java.util.*


/**
 * Created by nethmih on 13.04.19.
 */

object ServerUtil {

    fun generateRandomId(): BigInteger{
        return BigInteger.valueOf(UUID.randomUUID().mostSignificantBits and MAX_VALUE)
    /*    val rand = Random()
        return BigInteger(999999999, rand)*/

    }

    fun convertToLocalDate(date: Date) : LocalDate {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    }
}