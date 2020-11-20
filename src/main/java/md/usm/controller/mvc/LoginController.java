package md.usm.controller.mvc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import md.usm.model.user.User;
import md.usm.repo.UserRepo;

import static md.usm.utils.Util.extractUserDetailsForUser;

@Controller
public class LoginController {

    private UserRepo userRepo;

    private UserDetailsService userDetailsService;

    public LoginController(final UserRepo userRepo, final UserDetailsService userDetailsService) {
        this.userRepo = userRepo;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping("/login")
    String login(HttpServletRequest request) {
        return request.getRemoteUser() == null ? "login" : "redirect:/";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

    @RequestMapping("/register")
    String registerPage(HttpServletRequest request) {
        return request.getRemoteUser() == null ? "register" : "redirect:/";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    String register(User user, HttpServletRequest request) throws ServletException {
        if (userRepo.findByEmail(user.getEmail()) == null) {
            userRepo.save(user);
            ((InMemoryUserDetailsManager) userDetailsService).createUser(extractUserDetailsForUser(user));
            request.login(user.getEmail(), user.getPassword());
            return "redirect:/";
        }
        return "redirect:/register?error";
    }

}