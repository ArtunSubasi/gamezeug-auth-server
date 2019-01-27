package com.gamezeug.authserver.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/** Overrides the default login page with a custom login page */
@Controller
class LoginController {

    @GetMapping("/login")
    fun login(): String {
        return "login"
    }

}