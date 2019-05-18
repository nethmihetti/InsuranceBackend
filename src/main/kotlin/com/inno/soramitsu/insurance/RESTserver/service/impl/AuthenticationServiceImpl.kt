package com.inno.soramitsu.insurance.RESTserver.service.impl

import com.inno.soramitsu.insurance.RESTserver.dao.AgentRepository
import com.inno.soramitsu.insurance.RESTserver.dao.CompanyRepository
import com.inno.soramitsu.insurance.RESTserver.model.Agent
import com.inno.soramitsu.insurance.RESTserver.model.AgentSignUpRequestBody
import com.inno.soramitsu.insurance.RESTserver.model.Company
import com.inno.soramitsu.insurance.RESTserver.service.AuthenticationService
import com.inno.soramitsu.insurance.RESTserver.util.ServerUtil
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by nethmih on 17.05.19.
 */

@Service("UserDetailsService")
class AuthenticationServiceImpl(private val agentRepository: AgentRepository,
                                private val companyRepository: CompanyRepository): UserDetailsService, AuthenticationService {

    @Transactional(readOnly = true)
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user: Agent = agentRepository.findByUsername(username)
        return User(user.username, user.password, emptyList())
    }

    override fun postNewAgent(agentSignUpRequestBody: AgentSignUpRequestBody) : Agent {

        val company: Company = companyRepository.findByCompanyid(agentSignUpRequestBody.companyId)

        val newAgent = Agent(ServerUtil.generateRandomId(), agentSignUpRequestBody.username, agentSignUpRequestBody.password, company)

        return agentRepository.save(newAgent)
    }
}