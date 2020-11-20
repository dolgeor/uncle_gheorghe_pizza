package md.usm.utils;

import java.util.function.Function;

import static java.lang.String.format;

import md.usm.model.order.Order;
import md.usm.model.order.OrderItem;

public class FormattingUtil {

    public static Function<Double, String> formatPrice = d -> format("%.2f", d);

    private static Function<OrderItem, String> formatOrderItemDetails = item ->
            format("%-15s %10.2f * %3d%n",
                    item.getProduct().getName(),
                    item.getProduct().getPrice(),
                    item.getQuantity());

    public static String generateOrderInfoReport(final Order order) {
        final StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append(format("Dear %s,%n%n", order.getCustomer()));
        reportBuilder.append("Here are you order details:");
        reportBuilder.append("\n\n");
        order.getOrderItems().forEach(item -> reportBuilder.append(formatOrderItemDetails.apply(item)));
        reportBuilder.append("\n\n");
        reportBuilder.append(format("SubTotal: %18.2f MDL%n", order.getSubTotal()));
        reportBuilder.append(format("Delivery: %18.2f MDL%n", order.getDelivery()));
        reportBuilder.append(format("Total: %21.2f MDL%n", order.getTotal()));

        return reportBuilder.toString();
    }

    public static String generateContactUsMail(final String name,
                                               final String email,
                                               final String comments) {
        StringBuilder mailInfo = new StringBuilder();
        mailInfo.append(format("Name: %s%n", name));
        mailInfo.append(format("Email: %s%n", email));
        mailInfo.append(format("Comment: %s%n", comments));
        return mailInfo.toString();
    }
}
