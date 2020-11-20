package md.usm.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import md.usm.model.order.Order;
import md.usm.repo.OrderRepo;

@RestController
@RequestMapping("rest/orders")
public class OrderRestController {

    @Autowired
    private OrderRepo repo;

    @GetMapping(produces = "application/json")
    public List<Order> getOrders() {
        return (List<Order>) repo.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Order getOrderById(@PathVariable final String id) {
        return repo.findById(id).get();
    }
}