package com.inno.soramitsu.insurance.RESTserver.security

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import com.fasterxml.jackson.databind.ObjectMapper
import com.inno.soramitsu.insurance.RESTserver.model.InsuranceUsers
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
import org.springframework.security.core.userdetails.User


/**
 * Created by nethmih on 16.05.19.
 */

class JWTAuthenticationFilter(authManager: AuthenticationManager): UsernamePasswordAuthenticationFilter() {

    init {
        authenticationManager = authManager
    }

    @Throws(AuthenticationException::class, IOException::class, ServletException::class)
    override fun attemptAuthentication(req: HttpServletRequest, res: HttpServletResponse): Authentication {

        val creds = ObjectMapper()
                .readValue(req.inputStream, InsuranceUsers::class.java)

        return authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        creds.username,
                        creds.password,
                        emptyList<GrantedAuthority>())
        )

    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain,
                                          auth: Authentication) {


        val token: String = Jwts.builder()
                .setSubject((auth.principal as User).username)
                .setExpiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact()

        res.addHeader(HEADER_STRING, "$TOKEN_PREFIX $token")
    }
}