package com.inno.soramitsu.insurance.RESTserver.util.exception

/**
 * Created by nethmih on 14.04.19.
 */

object ServerErrorMessages {

    var INTERNAL_ERROR = "An internal error has occurred while performing the request"
    var TYPE_ERROR = "A type error has occurred in request parameters"
    var REQUIRED_PARAM_ERROR = "Required parameters are missing in the request"
    var INVALID_PARAM_ERROR = "An invalid parameter has sent in the request"
    var DATABASE_ERROR = "Database error"
    var NO_RESOURCE = "Resource not found"
    var CONVERSION_ERROR = "Failed to convert String to JSON"
    var UNAUTHORIZED = "The namespace is not authorized"
    var DUPLICATE_VALUE = "Duplicate key value violation"
    var WRONG_FORMAT = "The given string is in incorrect format"
    var NULL_RESPONSE_FROM_DATABASE = "The queried result doesnt exist "

}