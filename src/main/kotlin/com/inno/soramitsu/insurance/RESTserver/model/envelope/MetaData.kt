package com.inno.soramitsu.insurance.RESTserver.model.envelope

import com.fasterxml.jackson.annotation.JsonInclude
import lombok.Getter
import lombok.Setter

/**
 * Created by nethmih on 29.05.19.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
class MetaData {
    var currentPage: Int? = null
    var totalPages: Int? = null
    var itemsPerPage: Int? = null
    var totalItems: Long? = null
}