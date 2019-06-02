package com.inno.soramitsu.insurance.RESTserver.security

/**
 * Created by nethmih on 16.05.19.
 */

class Constants {
    object SecurityConstants {
        const val SECRET:String = "SecretKeyToGenJWTs"
        const val EXPIRATION_TIME: Long = 864000000 // 10 days
        const val TOKEN_PREFIX:String = "Bearer "
        const val HEADER_STRING:String = "Authorization"
        const val SIGN_UP_URL_AGENT:String = "/api/V1/agents/signUp"
        const val SIGN_UP_URL_CLIENT:String = "/api/V1/clients/signUp"
    }
}



