package com.kamila.food.core.security.authorizationserver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping("/login")
    public String login() {
        return "pages/login";
    }

    @GetMapping("/oauth/confirm_access")
    public String approval() {
        return "pages/oauth2_approval";
    }

}
