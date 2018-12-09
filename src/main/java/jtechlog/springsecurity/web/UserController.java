package jtechlog.springsecurity.web;

import jtechlog.springsecurity.backend.User;
import jtechlog.springsecurity.backend.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/")
    public ModelAndView index(@AuthenticationPrincipal User user) {
        LOGGER.debug("Bejelentkezett felhasználó: {}", user);
        return new ModelAndView("index", Map.of("users", userService.listUsers(), "user", new User()));
    }

    @PostMapping(value = "/")
    public String addUser(@ModelAttribute User user) {
        userService.addUser(user);
        return "redirect:/";
    }
}
