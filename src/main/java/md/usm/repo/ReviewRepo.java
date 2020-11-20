package md.usm.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import md.usm.model.product.Product;
import md.usm.model.product.Review;

@Repository
public interface ReviewRepo extends CrudRepository<Review, Integer> {

    List<Review> findByProduct(final Product product);
}
