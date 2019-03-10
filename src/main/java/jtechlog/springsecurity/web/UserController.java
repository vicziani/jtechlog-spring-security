package jtechlog.springsecurity.web;

import jtechlog.springsecurity.backend.User;
import jtechlog.springsecurity.backend.UserService;
import jtechlog.springsecurity.backend.UsernameInUrlAuthenticationFailureHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ModelAndView login(HttpSession httpSession) {
        String lastUsername = (String) httpSession.getAttribute(UsernameInUrlAuthenticationFailureHandler.LAST_USERNAME_KEY);
        if (lastUsername != null) {
            httpSession.removeAttribute(UsernameInUrlAuthenticationFailureHandler.LAST_USERNAME_KEY);
        }

        return new ModelAndView("login", "lastUsername", lastUsername);
    }

    @GetMapping(value = "/")
    public ModelAndView index(@AuthenticationPrincipal User user) {
        logger.debug("Logged in user: {}", user);
        return new ModelAndView("index", Map.of("users", userService.listUsers(), "user", new User()));
    }

    @PostMapping(value = "/")
    public String addUser(@ModelAttribute User user) {
        userService.addUser(user);
        return "redirect:/";
    }
}
