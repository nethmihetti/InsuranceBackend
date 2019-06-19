package com.inno.soramitsu.insurance.RESTserver.util.exception

/**
 * Created by nethmih on 14.04.19.
 */

class ServerExceptions(override val message: String, val errorCode: ServerErrorCodes, val additionalInfo: String) : RuntimeException()

class ServerExceptionTO(var message: String? = "", var code: String = "", var additionalInfo: String? = "")
