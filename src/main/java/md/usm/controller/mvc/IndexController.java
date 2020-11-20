package md.usm.controller.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import md.usm.model.product.Category;
import md.usm.model.product.Product;
import md.usm.repo.ProductRepo;
import md.usm.repo.RatingRepo;
import md.usm.service.CartService;
import md.usm.service.MailingService;

import static md.usm.model.product.Category.DESSERT;
import static md.usm.model.product.Category.PIZZA;
import static md.usm.model.product.Category.SALADS;
import static md.usm.utils.FormattingUtil.generateContactUsMail;

@Controller
public class IndexController {

    private String uncleGeorgeEmail;

    private MailingService mailingService;

    private RatingRepo ratingRepo;

    private ProductRepo productRepo;

    private CartService cartService;

    public IndexController(@Value("${spring.mail.username}") final String uncleGeorgeEmail,
                           final MailingService mailingService, final RatingRepo ratingRepo,
                           final ProductRepo productRepo, final CartService cartService) {
        this.uncleGeorgeEmail = uncleGeorgeEmail;
        this.mailingService = mailingService;
        this.ratingRepo = ratingRepo;
        this.productRepo = productRepo;
        this.cartService = cartService;
    }

    @RequestMapping("/")
    String index(Model model, HttpServletRequest request) {

        List<Product> top = new ArrayList<>();
        ratingRepo.getTopRatedProductIds().subList(0, 3).forEach(id -> top.add(productRepo.findById(id).get()));
        Map<Category, String> categories = new HashMap<>();
        Stream.of(PIZZA, SALADS, DESSERT)
                .forEach(category -> categories.put(category,
                        productRepo.findByCategory(category).stream().findFirst().get()
                                .getImages().stream().findFirst().get()));
        model.addAttribute("topProducts", top);
        model.addAttribute("categories", categories);
        model.addAttribute("cartSize", cartService.getCartSize(request.getRemoteUser()));
        return "index";
    }

    @RequestMapping("/contactUs")
    String contactUs(final String name,
                     final String email,
                     final String comments) {
        mailingService.send(uncleGeorgeEmail, "Contact Us", generateContactUsMail(name, email, comments));
        return "redirect:/";
    }
}