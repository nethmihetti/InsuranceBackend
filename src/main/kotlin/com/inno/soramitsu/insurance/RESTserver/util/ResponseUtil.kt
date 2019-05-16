package com.inno.soramitsu.insurance.RESTserver.util

import com.inno.soramitsu.insurance.RESTserver.model.envelope.EnvelopedResponse
/**
 * Created by nethmih on 14.04.19.
 */

object ResponseUtil {


    fun <T> generateResponse(data: T): EnvelopedResponse<Any> {

        val response: EnvelopedResponse<Any> = EnvelopedResponse()
        response.data = data

        return response
    }

}