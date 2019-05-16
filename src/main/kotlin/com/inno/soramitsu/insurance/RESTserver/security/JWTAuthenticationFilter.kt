package com.inno.soramitsu.insurance.RESTserver.security

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import com.fasterxml.jackson.databind.ObjectMapper
import com.inno.soramitsu.insurance.RESTserver.model.Agent
import com.inno.soramitsu.insurance.RESTserver.security.Constants.SecurityConstants.EXPIRATION_TIME
import com.inno.soramitsu.insurance.RESTserver.security.Constants.SecurityConstants.HEADER_STRING
import com.inno.soramitsu.insurance.RESTserver.security.Constants.SecurityConstants.SECRET
import com.inno.soramitsu.insurance.RESTserver.security.Constants.SecurityConstants.TOKEN_PREFIX
import org.springframework.security.core.GrantedAuthority
import javax.servlet.FilterChain
import java.io.IOException
import java.util.Date
import javax.servlet.ServletException

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm


/**
 * Created by nethmih on 16.05.19.
 */

class JWTAuthenticationFilter(private val authManager: AuthenticationManager): UsernamePasswordAuthenticationFilter() {

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(req: HttpServletRequest, res: HttpServletResponse): Authentication {
        try {
            val creds = ObjectMapper()
                    .readValue(req.inputStream, Agent::class.java)

            return this.authManager.authenticate(
                    UsernamePasswordAuthenticationToken(
                            creds.email,
                            creds.password,
                            ArrayList<GrantedAuthority>())
            )
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain,
                                          auth: Authentication) {


        val token: String = Jwts.builder()
                .setSubject((auth.principal as Agent).email)
                .setExpiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact()

        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token)
    }
}