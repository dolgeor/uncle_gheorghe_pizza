package md.usm.controller.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import md.usm.model.product.Product;
import md.usm.repo.ProductRepo;

@RestController
@RequestMapping("/api")
public class MyRestController {

    @Autowired
    ProductRepo productRepo;

    @GetMapping("hello/{name}")
    public String getBook(@PathVariable String name) {
        return "Hello, " + name;
    }

    @GetMapping("products")
    public List<Product> getAllPizzas() {
        return (List<Product>) productRepo.findAll();
    }
}
