package md.usm.controller.mvc;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import md.usm.model.product.Category;
import md.usm.model.product.Product;
import md.usm.model.product.Rate;
import md.usm.model.product.Review;
import md.usm.repo.ProductRepo;
import md.usm.repo.RatingRepo;
import md.usm.repo.ReviewRepo;
import md.usm.service.CartService;

import static md.usm.utils.InitialDataGenerator.computeProducts;
import static md.usm.utils.InitialDataGenerator.getRatingsOfProduct;
import static md.usm.utils.InitialDataGenerator.getReviewsFroProduct;

@Controller
public class ProductController {

    private final ProductRepo productRepo;

    private final ReviewRepo reviewRepo;

    private final RatingRepo ratingRepo;

    private final CartService cartService;

    public ProductController(final ProductRepo productRepo, final ReviewRepo reviewRepo,
                             final RatingRepo ratingRepo, final CartService cartService) {
        this.productRepo = productRepo;
        this.reviewRepo = reviewRepo;
        this.ratingRepo = ratingRepo;
        this.cartService = cartService;
        this.productRepo.saveAll(computeProducts());
        final Iterable<Product> products = this.productRepo.findAll();
        products.forEach(product -> this.reviewRepo.saveAll(getReviewsFroProduct(product)));
        products.forEach(product -> this.ratingRepo.saveAll(getRatingsOfProduct(product)));
    }

    @RequestMapping("/products/{category}/{id}")
    String product(@PathVariable String category, @PathVariable Integer id, Model model, HttpServletRequest request) {
        final Product product = productRepo.findByCategoryAndId(Category.get(category), id);
        model.addAttribute("product", product);
        model.addAttribute("reviews", reviewRepo.findByProduct(product));
        final List<Rate> rates = ratingRepo.findByProduct(product);
        final Double rating = rates.stream()
                .map(Rate::getValue)
                .mapToDouble(Double::doubleValue)
                .sum() / rates.size();
        model.addAttribute("rating", String.format("%.1f", rating));
        model.addAttribute("cartSize", cartService.getCartSize(request.getRemoteUser()));
        return "product";
    }

    @PostMapping("/products/{category}/{id}/addReview")
    String addReview(@PathVariable final String category,
                     @PathVariable final Integer id,
                     final String review, final String name) {
        final Product product = productRepo.findByCategoryAndId(Category.get(category), id);
        reviewRepo.save(Review.builder().product(product).review(review).author(name).build());
        return "redirect:/products/{category}/{id}";
    }

    @PostMapping("/products/{category}/{id}/addVote")
    String addVote(@PathVariable final String category,
                   @PathVariable final Integer id, final Integer stars) {
        final Product product = productRepo.findByCategoryAndId(Category.get(category), id);
        ratingRepo.save(Rate.builder().product(product).value(stars.doubleValue()).build());
        return "redirect:/products/{category}/{id}";
    }

    @RequestMapping("/products/{category}")
    String category(@PathVariable String category, Model model, HttpServletRequest request) {
        model.addAttribute("category", category);
        model.addAttribute("products", productRepo.findByCategory(Category.get(category)));
        model.addAttribute("cartSize", cartService.getCartSize(request.getRemoteUser()));
        return "category";
    }

    @RequestMapping("/categories")
    String categories(Model model, HttpServletRequest request) {
        Map<Category, List<Product>> categories = new TreeMap<>();
        Stream.of(Category.values()).forEach(category -> categories.put(category, productRepo.findTop4ByCategory(category)));
        model.addAttribute("categories", categories);
        model.addAttribute("cartSize", cartService.getCartSize(request.getRemoteUser()));
        return "categories";
    }
}
