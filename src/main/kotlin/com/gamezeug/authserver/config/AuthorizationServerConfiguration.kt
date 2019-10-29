package com.gamezeug.authserver.config

import com.gamezeug.authserver.config.jwt.JwtCustomHeadersAccessTokenConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory
import org.springframework.core.io.ClassPathResource
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.RSAKey
import java.security.KeyPair
import java.security.interfaces.RSAPublicKey


@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfiguration(
        @Autowired private val authenticationManager: AuthenticationManager,
        @Value("\${OAUTH2_CLIENT_REDIRECT_URI}") private val oauth2ClientRedirectUri: String
) : AuthorizationServerConfigurerAdapter() {

    private val JWK_KID = "gamezeug-key-id"

    override fun configure(oauthServer: AuthorizationServerSecurityConfigurer) {
        oauthServer.tokenKeyAccess("permitAll()")
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .checkTokenAccess("isAuthenticated()")
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
                .withClient("gamezeug-tables")
                .secret("gamezeug-secret")
                .authorizedGrantTypes("authorization_code")
                .scopes("read")
                .autoApprove(true)
                .redirectUris(oauth2ClientRedirectUri)
                .accessTokenValiditySeconds(3600)
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter())
                .authenticationManager(authenticationManager)
    }

    @Bean
    fun tokenStore(): TokenStore {
        return JwtTokenStore(accessTokenConverter())
    }

    @Bean
    fun keyPair(): KeyPair {
        val keyStoreFile = ClassPathResource("jwt/gamezeug-jwt.jks")
        val keyStoreFactory = KeyStoreKeyFactory(keyStoreFile, "YR58P9ZdjVvWuaCK5Rink4ZB".toCharArray())
        return keyStoreFactory.getKeyPair("gamezeug-oauth-jwt")
    }

    @Bean
    fun accessTokenConverter(): JwtAccessTokenConverter {
        val headers = mapOf("kid" to JWK_KID)
        return JwtCustomHeadersAccessTokenConverter(headers, keyPair())
    }

    @Bean
    @Primary
    fun tokenServices(): DefaultTokenServices {
        val defaultTokenServices = DefaultTokenServices()
        defaultTokenServices.setTokenStore(tokenStore())
        defaultTokenServices.setSupportRefreshToken(true)
        return defaultTokenServices
    }

    @Bean
    fun jwkSet(): JWKSet {
        val builder = RSAKey.Builder(keyPair().public as RSAPublicKey)
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .keyID(JWK_KID)
        return JWKSet(builder.build())
    }

}