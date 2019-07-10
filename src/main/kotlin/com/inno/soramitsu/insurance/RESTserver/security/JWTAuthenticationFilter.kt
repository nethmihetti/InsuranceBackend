package com.inno.soramitsu.insurance.RESTserver.security

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import com.fasterxml.jackson.databind.ObjectMapper
import com.inno.soramitsu.insurance.RESTserver.dao.AgentRepository
import com.inno.soramitsu.insurance.RESTserver.model.Agent
import com.inno.soramitsu.insurance.RESTserver.model.InsuranceUsers
import com.inno.soramitsu.insurance.RESTserver.security.Constants.SecurityConstants.AGENT_HEADER_STRING
import com.inno.soramitsu.insurance.RESTserver.security.Constants.SecurityConstants.EXPIRATION_TIME
import com.inno.soramitsu.insurance.RESTserver.security.Constants.SecurityConstants.HEADER_STRING
import com.inno.soramitsu.insurance.RESTserver.security.Constants.SecurityConstants.SECRET
import com.inno.soramitsu.insurance.RESTserver.security.Constants.SecurityConstants.TOKEN_PREFIX
import com.inno.soramitsu.insurance.RESTserver.service.AuthenticationService
import org.springframework.security.core.GrantedAuthority
import javax.servlet.FilterChain
import java.io.IOException
import java.util.Date
import javax.servlet.ServletException

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service


/**
 * Created by nethmih on 16.05.19.
 */

class JWTAuthenticationFilter(authManager: AuthenticationManager): UsernamePasswordAuthenticationFilter() {

    init {
        authenticationManager = authManager
    }

    //private val authServiceProvider = AuthenticationService()

    //private val authenticationServiceProvider: authenticationService

    private lateinit var userType: String
    private lateinit var username: String

    private var d = Dumb()
    /*@Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }*/

    @Throws(AuthenticationException::class, IOException::class, ServletException::class)
    override fun attemptAuthentication(req: HttpServletRequest, res: HttpServletResponse): Authentication {

        val creds = ObjectMapper()
                .readValue(req.inputStream, InsuranceUsers::class.java)

        userType = creds.userType
        username = creds.username

        System.out.println("1")

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

        if (userType == "agent") {

            val agent: Agent = d.getAgentDetails(username)
            res.addHeader(AGENT_HEADER_STRING, agent.company.companyid.toString())
        }
        System.out.println("2")
    }
}

@Service
abstract class Dumb (private val agentRepository: AgentRepository): AuthenticationService {

   /* @Autowired
    private lateinit var authenticationService: AuthenticationServiceImpl
*/

 //val s =authenticationService



    override fun getAgentDetails(agentUsername: String) : Agent {
        return agentRepository.findByInsuranceuserUsername(agentUsername)
    }

}