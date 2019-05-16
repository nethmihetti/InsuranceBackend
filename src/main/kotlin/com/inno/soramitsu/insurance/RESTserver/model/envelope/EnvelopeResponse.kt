package com.inno.soramitsu.insurance.RESTserver.model.envelope

import com.inno.soramitsu.insurance.RESTserver.util.exception.ServerExceptionTO
import org.springframework.hateoas.ResourceSupport
/**
 * Created by nethmih on 14.04.19.
 */

class EnvelopedResponse<T> {

    var data: T? = null
    var error: List<ServerExceptionTO> = listOf()
}