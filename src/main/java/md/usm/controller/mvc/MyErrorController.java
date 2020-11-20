package md.usm.controller.mvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import md.usm.service.CartService;

@Controller
public class MyErrorController implements ErrorController {

    @Autowired
    private CartService cartService;

    @RequestMapping("/error")
    public String handleError(Model model, HttpServletRequest request) {
        model.addAttribute("cartSize", cartService.getCartSize(request.getRemoteUser()));
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
