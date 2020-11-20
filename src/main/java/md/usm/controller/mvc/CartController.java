package md.usm.controller.mvc;

import java.io.IOException;

import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import md.usm.model.order.Cart;
import md.usm.model.order.Order;
import md.usm.model.user.User;
import md.usm.repo.OrderRepo;
import md.usm.repo.ProductRepo;
import md.usm.repo.UserRepo;
import md.usm.service.CartService;
import md.usm.service.MailingService;
import md.usm.utils.OrderReportingHelper;

import static md.usm.utils.FormattingUtil.formatPrice;
import static md.usm.utils.FormattingUtil.generateOrderInfoReport;

@Controller
public class CartController {

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    CartService cartService;

    @Autowired
    MailingService mailingService;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    UserRepo userRepo;

    @RequestMapping("/cart")
    String cart(Model model, HttpServletRequest request) {
        final Cart cart = cartService.getActiveCart(request.getRemoteUser());
        final Double subTotal = cartService.calculateTotal(cart);
        final Double delivery = subTotal < 500.0 ? 30.0 : 0.0;
        final Double total = subTotal + delivery;

        model.addAttribute("items", cart.getItems());
        model.addAttribute("subTotal", formatPrice.apply(subTotal));
        model.addAttribute("delivery", formatPrice.apply(delivery));
        model.addAttribute("total", formatPrice.apply(total));
        model.addAttribute("cartSize", cartService.getCartSize(request.getRemoteUser()));
        return "cart";
    }

    @RequestMapping(value = "/cart/add", method = RequestMethod.POST)
    public String addToCart(Integer id, Model model, HttpServletRequest request) {
        cartService.addToCart(request.getRemoteUser(), productRepo.findById(id).get(), 1);
        return "redirect:/cart";
    }

    @RequestMapping(value = "/cart/update", method = RequestMethod.POST)
    public String updateOrderItem(@RequestParam(name = "id") Integer id, @RequestParam(name = "quantity") Integer quantity, Model model) {
        cartService.updateOrderItem(id, quantity);
        return "redirect:/cart";
    }

    @RequestMapping(value = "/cart/delete", method = RequestMethod.POST)
    public String deleteOrderItem(@RequestParam(name = "id") Integer id, Model model, HttpServletRequest request) {
        cartService.deleteOrderItem(id, request.getRemoteUser());
        return "redirect:/cart";
    }

    @RequestMapping(value = "/cart/checkout", method = RequestMethod.POST)
    public String checkout(Model model, HttpServletRequest request) throws IOException {
        final User user = userRepo.findByEmail(request.getRemoteUser());
        final Cart cart = cartService.checkout(user.getEmail());
        final Double subTotal = cartService.calculateTotal(cart);
        final Double delivery = subTotal < 500.0 ? 30.0 : 0.0;
        final Double total = subTotal + delivery;
        Order order = Order.builder()
                .id(randomUUID().toString())
                .customer(user.getName())
                .customerEmail(user.getEmail())
                .customerPhone(user.getPhone())
                .customerPhone(user.getPhone())
                .customerAddress(user.getAddress())
                .orderItems(cart.getItems())
                .date(now())
                .subTotal(subTotal)
                .delivery(delivery)
                .total(total)
                .build();
        orderRepo.save(order);
        model.addAttribute("order", order);
        model.addAttribute("user", user);
        final String reportFile = OrderReportingHelper.createOrderReportFile(order);
        mailingService.sendWithAttachment(user.getEmail(), "order-info", generateOrderInfoReport(order), reportFile);
        return "checkout";
    }
}
