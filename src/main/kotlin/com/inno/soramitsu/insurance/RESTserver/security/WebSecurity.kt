package com.inno.soramitsu.insurance.RESTserver.security

import com.inno.soramitsu.insurance.RESTserver.security.Constants.SecurityConstants.SIGN_UP_URL
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

/**
 * Created by nethmih on 16.05.19.
 */

@EnableWebSecurity
class WebSecurity(private val userDetailsService: UserDetailsService,
                  private val bCryptPasswordEncoder: BCryptPasswordEncoder): WebSecurityConfigurerAdapter() {

    @Throws(Exception:: class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(JWTAuthenticationFilter(authenticationManager()))
                .addFilter(JWTAuthorizationFilter(authenticationManager()));
    }

    @Throws(Exception:: class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(this.bCryptPasswordEncoder)
    }

}