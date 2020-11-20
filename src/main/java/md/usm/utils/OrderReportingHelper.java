package md.usm.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import md.usm.model.order.Order;

public class OrderReportingHelper {

    public static String createOrderReportFile(final Order order) throws IOException {
        final String fileName = String.format("order-info-%s.xls", order.getId());
        final Workbook workbook = new HSSFWorkbook();
        final Sheet sheet = workbook.createSheet("Order-info");

        AtomicInteger rowIndex = new AtomicInteger();
        Row row = sheet.createRow(rowIndex.getAndIncrement());
        row.createCell(0).setCellValue("Customer");
        row.createCell(1).setCellValue(order.getCustomer());

        rowIndex.getAndIncrement();

        row = sheet.createRow(rowIndex.getAndIncrement());
        row.createCell(0).setCellValue("Product");
        row.createCell(1).setCellValue("Quantity");
        row.createCell(2).setCellValue("Price(MDL)");

        order.getOrderItems().forEach(item -> {
            Row itemRow = sheet.createRow(rowIndex.getAndIncrement());
            itemRow.createCell(0).setCellValue(item.getProduct().getName());
            itemRow.createCell(1).setCellValue(item.getQuantity());
            itemRow.createCell(2).setCellValue(item.getProduct().getPrice());
        });

        rowIndex.getAndIncrement();

        row = sheet.createRow(rowIndex.getAndIncrement());
        row.createCell(0).setCellValue("SubTotal");
        row.createCell(2).setCellValue(order.getSubTotal());

        row = sheet.createRow(rowIndex.getAndIncrement());
        row.createCell(0).setCellValue("Delivery");
        row.createCell(2).setCellValue(order.getDelivery());

        row = sheet.createRow(rowIndex.getAndIncrement());
        row.createCell(0).setCellValue("Total");
        row.createCell(2).setCellValue(order.getTotal());

        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();

        return fileName;
    }

    // public static void main(String[] args) throws Exception {
    //     final ArrayList<OrderItem> orderItems = new ArrayList<>();
    //     computeProducts().forEach(p -> orderItems.add(new OrderItem(p, new SecureRandom().nextInt(5) + 1)));
    //     final Cart cart = new Cart(orderItems);
    //     final Double subTotal = cart.getItems().stream().map(item -> item.getProduct().getPrice() * item.getQuantity()).mapToDouble(Double::doubleValue).sum();
    //     final Double delivery = subTotal < 500.0 ? 30.0 : 0.0;
    //     final Double total = subTotal + delivery;
    //     Order order = Order.builder()
    //             .id(516154861)
    //             .customer("Gheorghi Dolomanji")
    //             .orderItems(cart.getItems())
    //             .subTotal(subTotal)
    //             .delivery(delivery)
    //             .total(total)
    //             .build();
    //     System.out.println(generateOrderInfoReport(order));
    //     createOrderReportFile(order);
    // }
}