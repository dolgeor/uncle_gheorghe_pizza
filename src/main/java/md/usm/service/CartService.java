package md.usm.service;

import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import org.springframework.stereotype.Service;

import md.usm.model.order.Cart;
import md.usm.model.order.OrderItem;
import md.usm.model.product.Product;
import md.usm.repo.CartRepo;
import md.usm.repo.OrderItemRepo;

@Service
public class CartService {

    private CartRepo cartRepo;

    private OrderItemRepo orderItemRepo;

    public CartService(final CartRepo cartRepo, final OrderItemRepo orderItemRepo) {
        this.cartRepo = cartRepo;
        this.orderItemRepo = orderItemRepo;
    }

    public Cart getActiveCart(final String user) {
        final Cart activeCart = cartRepo.findByUserNameAndIsActiveTrue(user);
        return nonNull(activeCart) ? activeCart : cartRepo.save(Cart.builder().userName(user).build());
    }

    public Integer getCartSize(final String user) {
        return isNull(user) ? 0 : getActiveCart(user).getItems().size();
    }

    public void addToCart(final String user, final Product product, final Integer quantity) {
        final Cart cart = getActiveCart(user);
        if (cart.getItems().stream().noneMatch(i -> i.getProduct().equals(product))) {
            final OrderItem item = orderItemRepo.save(new OrderItem(product, quantity));
            cart.getItems().add(item);
            cartRepo.save(cart);
        }
    }

    public void updateOrderItem(final Integer itemId, final Integer quantity) {
        orderItemRepo.findById(itemId).ifPresent(item -> {
            item.setQuantity(quantity);
            orderItemRepo.save(item);
        });
    }

    public void deleteOrderItem(final Integer id, final String user) {
        final Cart cart = getActiveCart(user);
        final Optional<OrderItem> item = cart.getItems().stream().filter(i -> i.getId().equals(id)).findFirst();
        if (item.isPresent()) {
            cart.getItems().remove(item.get());
            cartRepo.save(cart);
            orderItemRepo.deleteById(id);
        }
    }

    public Cart checkout(final String user) {
        final Cart cart = getActiveCart(user);
        cart.setIsActive(false);
        return cartRepo.save(cart);
    }

    public Double calculateTotal(final Cart cart) {
        return cart.getItems().stream().map(item -> item.getProduct().getPrice() * item.getQuantity()).mapToDouble(Double::doubleValue).sum();
    }

}
