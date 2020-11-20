package md.usm.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import md.usm.model.product.Category;
import md.usm.model.product.Product;

@Repository
public interface ProductRepo extends CrudRepository<Product, Integer> {

    List<Product> findByCategory(final Category category);

    Product findByCategoryAndId(final Category category, final Integer id);

    List<Product> findTop4ByCategory(final Category category);
}
