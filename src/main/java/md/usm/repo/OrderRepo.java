package md.usm.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import md.usm.model.order.Order;

@Repository
public interface OrderRepo extends CrudRepository<Order, String> {

}
