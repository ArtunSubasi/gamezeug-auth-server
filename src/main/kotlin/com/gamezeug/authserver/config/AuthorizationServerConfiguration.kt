package com.gamezeug.authserver.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer


@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfiguration(
        private val passwordEncoder: BCryptPasswordEncoder,
        @Value("\${OAUTH2_CLIENT_REDIRECT_URI}") private val oauth2ClientRedirectUri: String
) : AuthorizationServerConfigurerAdapter() {

    override fun configure(oauthServer: AuthorizationServerSecurityConfigurer) {
        oauthServer.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
                .withClient("gamezeug-tables")
                .authorizedGrantTypes("authorization_code")
                .scopes("read")
                .autoApprove(true)
                .redirectUris(oauth2ClientRedirectUri)
                .accessTokenValiditySeconds(3600)
    }

}