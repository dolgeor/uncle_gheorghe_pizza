package md.usm.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import md.usm.model.product.Product;
import md.usm.model.product.Rate;

@Repository
public interface RatingRepo extends CrudRepository<Rate, Integer> {

    List<Rate> findByProduct(final Product product);

    @Query("select product.id from Rate group by product order by avg(value) desc")
    List<Integer> getTopRatedProductIds();
}