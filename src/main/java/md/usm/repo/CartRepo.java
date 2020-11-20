package md.usm.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import md.usm.model.order.Cart;

@Repository
public interface CartRepo extends CrudRepository<Cart, Integer> {

    Cart findByUserNameAndIsActiveTrue(final String userName);
}
