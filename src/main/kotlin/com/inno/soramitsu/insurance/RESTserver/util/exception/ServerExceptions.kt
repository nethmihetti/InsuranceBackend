package com.inno.soramitsu.insurance.RESTserver.util.exception

/**
 * Created by nethmih on 14.04.19.
 */

class ServerExceptions(override val message: String, val errorCode: ServerErrorCodes, additionalInfo: String) : RuntimeException() {

    val additionalInfo: String? = additionalInfo

}

class ServerExceptionTO(var message: String? = "", var code: String = "") {
    constructor(message: String?, code: String, additionalInfo: String?) : this(message, code)
}
