package com.inno.soramitsu.insurance.RESTserver.model.envelope

import com.fasterxml.jackson.annotation.JsonInclude
import com.inno.soramitsu.insurance.RESTserver.util.exception.ServerExceptionTO
import org.springframework.hateoas.ResourceSupport
import java.util.*

/**
 * Created by nethmih on 14.04.19.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
class EnvelopedResponse<T>: ResourceSupport() {

    var data: T? = null
    var meta: MetaData? = null
    var error: List<ServerExceptionTO>? = null

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("Response Body:").append(if (this.data != null) data.toString() else "")
        stringBuilder.append("Error:").append(if (this.error != null) error.toString() else "")

        return stringBuilder.toString()

    }

    override fun equals(any: Any?): Boolean {
        if (any is EnvelopedResponse<*>) {
            val other = any as EnvelopedResponse<*>?
            return this.data == other!!.data && this.meta == other.meta && this.error == other.error
        } else {
            return false
        }
    }

    override fun hashCode(): Int {
        return Objects.hashCode(this.data)
    }
}