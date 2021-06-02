package com.shotcutter.identity.api.v1;

import com.shotcutter.library.user.User;
import com.shotcutter.securitystarter.security.JWTPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/user")
@RestController
public class UserController {

    @GetMapping("me")
    User getCurrentUser(JWTPrincipal principal) {
        return principal.getPrincipal();
    }

}
