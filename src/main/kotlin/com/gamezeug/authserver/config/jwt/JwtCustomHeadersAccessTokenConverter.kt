package com.gamezeug.authserver.config.jwt

import org.springframework.security.jwt.JwtHelper
import org.springframework.security.jwt.crypto.sign.RsaSigner
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.common.util.JsonParserFactory
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import java.security.KeyPair
import java.security.interfaces.RSAPrivateKey

/**
 * Extension for JwtAccessTokenConverter in order to encode additional attributes in the JWT header.
 */
class JwtCustomHeadersAccessTokenConverter(private val customHeaders: Map<String, String>,
                                           keyPair: KeyPair) : JwtAccessTokenConverter() {

    private val objectMapper = JsonParserFactory.create()
    private val signer: RsaSigner

    init {
        super.setKeyPair(keyPair)
        this.signer = RsaSigner(keyPair.private as RSAPrivateKey)
    }

    override fun encode(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication): String {
        val content = this.objectMapper.formatMap(accessTokenConverter.convertAccessToken(accessToken, authentication))
        return JwtHelper.encode(content, signer, customHeaders).encoded
    }

}