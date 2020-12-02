package gr.nikolis.controller;

import gr.nikolis.mappings.login.LoginMappings;
import gr.nikolis.mappings.login.ViewNames;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping(LoginMappings.LOGIN)
    public String login() {
        return ViewNames.LOGIN;
    }

    @GetMapping(LoginMappings.USER)
    public String userIndex() {
        return ViewNames.USER_INDEX;
    }

    @GetMapping(LoginMappings.ACCESS_DENIED)
    public String accessDenied() {
        return ViewNames.ERROR_ACCESS;
    }

    @GetMapping(LoginMappings.USER_DISABLED)
    public String userDisabled() {
        return ViewNames.USER_DISABLED;
    }
}
