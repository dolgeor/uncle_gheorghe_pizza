package md.usm.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import md.usm.model.product.Category;
import md.usm.model.product.Product;
import md.usm.repo.ProductRepo;

@RestController
@RequestMapping("rest/products")
public class ProductRestController {

    @Autowired
    private ProductRepo repo;

    @GetMapping(produces = "application/json")
    public List<Product> getProducts() {
        return (List<Product>) repo.findAll();
    }

    @GetMapping(value = "/{category}", produces = "application/json")
    public List<Product> getProductsByCategory(@PathVariable final String category) {
        return repo.findByCategory(Category.valueOf(category.toUpperCase()));
    }

    @GetMapping(value = "/{category}/{id}", produces = "application/json")
    public Product getProductByCategoryAndId(@PathVariable final String category, @PathVariable final Integer id) {
        return repo.findByCategoryAndId(Category.valueOf(category.toUpperCase()), id);
    }
}