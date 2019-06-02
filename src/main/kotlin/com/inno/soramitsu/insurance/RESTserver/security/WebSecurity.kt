package com.inno.soramitsu.insurance.RESTserver.security

import com.google.common.collect.ImmutableList
import com.inno.soramitsu.insurance.RESTserver.security.Constants.SecurityConstants.SIGN_UP_URL_AGENT
import com.inno.soramitsu.insurance.RESTserver.security.Constants.SecurityConstants.SIGN_UP_URL_CLIENT
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

/**
 * Created by nethmih on 16.05.19.
 */

@Configuration
@EnableWebSecurity
class WebSecurity(@Qualifier("UserDetailsService") private val userDetailsService: UserDetailsService):
        WebSecurityConfigurerAdapter() {

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(http: HttpSecurity) {

        http.cors()
                .and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL_AGENT).permitAll()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL_CLIENT).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(JWTAuthenticationFilter(authenticationManager()))
                .addFilter(JWTAuthorizationFilter(authenticationManager()))

    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder())
    }

    override fun configure(web: WebSecurity?) {
        web!!.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html/**",
                "/api/V1/signUp/**",
                "/webjars/**")
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = ImmutableList.of("*")
        configuration.allowedMethods = ImmutableList.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
        configuration.allowedHeaders = ImmutableList.of("Content-Language", "Cache-Control", "Content-Type",
                "Expires", "Authorization", "Access-Control-Allow-Headers")
        configuration.exposedHeaders = ImmutableList.of("Authorization", "Access-Control-Allow-Headers")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}

