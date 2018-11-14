package com.gamezeug.authserver.config

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer


@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfiguration(private val passwordEncoder: BCryptPasswordEncoder) : AuthorizationServerConfigurerAdapter() {
    
    private val log = LoggerFactory.getLogger(AuthorizationServerConfiguration::class.java)

    @Throws(Exception::class)
    override fun configure(oauthServer: AuthorizationServerSecurityConfigurer) {
        oauthServer.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
        log.info("Hallo, Welt!")
    }

    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
                .withClient("SampleClientId")
                .secret(passwordEncoder.encode("secret"))
                .authorizedGrantTypes("authorization_code")
                .scopes("read")
                .autoApprove(true)
                .redirectUris("http://localhost:20000")
                .accessTokenValiditySeconds(3600)
    }

}