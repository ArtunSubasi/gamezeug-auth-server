package com.gamezeug.authserver.controller

import org.springframework.web.bind.annotation.GetMapping
import com.nimbusds.jose.jwk.JWKSet
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController

@RestController
class JwkSetRestController(@Autowired private val jwkSet: JWKSet) {

    @GetMapping("/.well-known/jwks.json")
    fun keys(): Map<String, Any> {
        return this.jwkSet.toJSONObject()
    }

}