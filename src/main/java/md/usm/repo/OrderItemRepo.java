package md.usm.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import md.usm.model.order.OrderItem;
import md.usm.model.product.Product;

@Repository
public interface OrderItemRepo extends CrudRepository<OrderItem, Integer> {

    OrderItem findByProduct(final Product product);
}
