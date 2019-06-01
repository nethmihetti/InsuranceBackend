package com.inno.soramitsu.insurance.RESTserver.util

import com.inno.soramitsu.insurance.RESTserver.util.exception.ServerErrorCodes
import com.inno.soramitsu.insurance.RESTserver.util.exception.ServerErrorMessages
import com.inno.soramitsu.insurance.RESTserver.util.exception.ServerExceptions
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import java.lang.Long.MAX_VALUE
import java.time.LocalDate
import java.time.ZoneId
import java.util.*


/**
 * Created by nethmih on 13.04.19.
 */

object ServerUtil {

    private const val DEFAULT_MAX_PAGE_SIZE = 100

    fun generateRandomId(): Long{
        return UUID.randomUUID().mostSignificantBits and MAX_VALUE
    }

    fun convertToLocalDate(date: Date) : LocalDate {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    }

    fun getPageSize(requestPageNumber: Int?, requestPageSize: Int?): Pageable {
        var page = requestPageNumber
        var size = requestPageSize
        if (requestPageSize == null) {
            if (requestPageNumber == null || requestPageNumber >= 0) {
                page = requestPageNumber ?: 0
                size = DEFAULT_MAX_PAGE_SIZE
            } else {
                throw ServerExceptions(ServerErrorMessages.INVALID_PARAM_ERROR, ServerErrorCodes.TYPE_INVALID, "Page index must not be less than zero!")
            }
        } else if (requestPageNumber == null) {
            page = 0
        } else if (requestPageNumber < 0) {
            throw ServerExceptions(ServerErrorMessages.INVALID_PARAM_ERROR, ServerErrorCodes.TYPE_INVALID, "Page index must not be less than zero!")
        } else if (requestPageSize < 0) {
            throw ServerExceptions(ServerErrorMessages.INVALID_PARAM_ERROR, ServerErrorCodes.TYPE_INVALID, "Page size must not be less than zero!")
        }
        return PageRequest.of(page!!, size!!)
    }

    fun validateInsuranceRequestDates(policyStartDate: Date, policyEndDate: Date) {
        if(policyStartDate.after(policyEndDate)) {
            throw ServerExceptions(ServerErrorMessages.INVALID_PARAM_ERROR, ServerErrorCodes.TYPE_INVALID, "policy start date should be before policy end date")
        }
        if(policyStartDate.equals(policyEndDate)) {
            throw ServerExceptions(ServerErrorMessages.INVALID_PARAM_ERROR, ServerErrorCodes.TYPE_INVALID, "policy start date and policy end date cannot be the same date")
        }
    }
}