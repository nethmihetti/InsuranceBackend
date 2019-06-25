package com.inno.soramitsu.insurance.RESTserver.service.impl

import com.inno.soramitsu.insurance.RESTserver.dao.AgentRepository
import com.inno.soramitsu.insurance.RESTserver.dao.CompanyRepository
import com.inno.soramitsu.insurance.RESTserver.dao.InsuranceUsersRepository
import com.inno.soramitsu.insurance.RESTserver.dao.ClientRepository
import com.inno.soramitsu.insurance.RESTserver.model.*
import com.inno.soramitsu.insurance.RESTserver.service.AuthenticationService
import com.inno.soramitsu.insurance.RESTserver.util.ServerUtil
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.inno.soramitsu.insurance.RESTserver.model.Client
import java.time.LocalDate

/**
 * Created by nethmih on 17.05.19.
 */

@Service("UserDetailsService")
@Transactional
class AuthenticationServiceImpl(private val agentRepository: AgentRepository,
                                private val companyRepository: CompanyRepository,
                                private val insuranceUsersRepository: InsuranceUsersRepository,
                                private val clientRepository: ClientRepository): UserDetailsService, AuthenticationService {

    @Transactional(readOnly = true)
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user: InsuranceUsers = insuranceUsersRepository.findByUsername(username)
        return User(user.username, user.password, emptyList())
    }

    override fun postNewAgent(agentSignUpRequestBody: SignUpRequestBody, companyId: Int) : Agent {

        val insuranceUser = InsuranceUsers(ServerUtil.generateRandomId(), agentSignUpRequestBody.username, agentSignUpRequestBody.password)
        val newUser = insuranceUsersRepository.save(insuranceUser)

        val company: Company = companyRepository.findByCompanyid(companyId.toLong())

        val newAgent = Agent(ServerUtil.generateRandomId(), "N/A", "N/A", "N/A", newUser, company)

        return agentRepository.save(newAgent)
    }

    override fun postNewClient(clientSignUpRequestBody: SignUpRequestBody) : Client {

        val insuranceUser = InsuranceUsers(ServerUtil.generateRandomId(), clientSignUpRequestBody.username, clientSignUpRequestBody.password)
        val newUser = insuranceUsersRepository.save(insuranceUser)

        val newClient = Client(ServerUtil.generateRandomId(), "N/A", "N/A", "N/A",
                clientSignUpRequestBody.username, ServerUtil.generateRandomId().toString(),
                ServerUtil.generateRandomId().toString(), "N/A", LocalDate.now(), newUser)

        return clientRepository.save(newClient)
    }
}
