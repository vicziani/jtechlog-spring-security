package jtechlog.springsecurity.web;

import jtechlog.springsecurity.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Web réteg.
 */
@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/index.html")
    public ModelAndView index() {
        LOGGER.debug("Bejelentkezett felhasználó: {}", new Object[] {SecurityContextHolder.getContext().getAuthentication().getPrincipal()});
        return new ModelAndView("index", "users", userService.listUsers());
    }

    @RequestMapping(value = "/addUser.html", method = RequestMethod.POST)    
    public String addUser(@RequestParam("username") String username, @RequestParam("password") String password,
        @RequestParam("roles") String roles) {
        userService.addUser(username, password, roles);
        return "redirect:/";
    }
}
