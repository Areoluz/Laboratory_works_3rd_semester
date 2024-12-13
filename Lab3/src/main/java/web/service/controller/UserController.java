package web.service.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal OAuth2User oAuth2User, Model model) {
        if (oAuth2User != null) {
            model.addAttribute("name", oAuth2User.getAttribute("name"));
            model.addAttribute("login", oAuth2User.getAttribute("login"));
            model.addAttribute("id", oAuth2User.getAttribute("id"));
            model.addAttribute("email", oAuth2User.getAttribute("email"));
        }
        return "user";
    }
}
