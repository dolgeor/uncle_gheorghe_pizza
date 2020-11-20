package md.usm.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

import md.usm.model.product.Product;
import md.usm.model.product.Rate;
import md.usm.model.product.Review;
import md.usm.model.user.User;

import static md.usm.model.product.Category.DESSERT;
import static md.usm.model.product.Category.DRINK;
import static md.usm.model.product.Category.PIZZA;
import static md.usm.model.product.Category.SALADS;

public class InitialDataGenerator {

    public static List<User> createUsers() {
        return asList(
                User.builder().email("dolgeor@yandex.ru")
                        .name("George")
                        .address("or. Tvardita, str. Ceapaeva")
                        .phone("+37368865925")
                        .password("123")
                        .confirmPassword("123")
                        .build(),
                User.builder().email("uncle.gheorghe.pizza@gmail.com")
                        .name("Uncle George")
                        .address("or. Chishinau")
                        .phone("+37368465845")
                        .password("123")
                        .confirmPassword("123")
                        .build());
    }

    public static List<Product> computeProducts() {
        final ArrayList<Product> products = new ArrayList<>();
        products.addAll(computePizzas());
        products.addAll(computeSalads());
        products.addAll(computeDesserts());
        products.addAll(computeDrinks());
        return products;
    }

    public static List<Review> getReviewsFroProduct(final Product product) {
        final Review pit = Review.builder().product(product).author("Pit").review("Amazing Pizza").build();
        final Review mike = Review.builder().product(product).author("Mike").review("Not bad, not bad").build();
        final Review ilona = Review.builder().product(product).author("Ilona").review("More please").build();
        final Review george = Review.builder().product(product).author("George").review("Very Hot, Cool").build();
        return Stream.of(pit, mike, ilona, george).filter(r -> new Random().nextInt() % 2 == 0).collect(Collectors.toList());
    }

    public static List<Rate> getRatingsOfProduct(final Product product) {
        return asList(
                Rate.builder().product(product).value(new Random().nextInt(3) + 3.0).build(),
                Rate.builder().product(product).value(new Random().nextInt(3) + 3.0).build(),
                Rate.builder().product(product).value(new Random().nextInt(3) + 3.0).build()
        );
    }

    private static List<Product> computePizzas() {
        List<Product> products = new ArrayList<>();
        products.add(Product.builder()
                .name("4 Cheeses")
                .price(new Double(80))
                .weight(380)
                .category(PIZZA)
                .description("Mozzarella cheese, parmigiano cheese, dor blue cheese, edam cheese.")
                .ingredients(asList("mozzarella cheese", "parmigiano cheese", "dor blue cheese", "edam cheese"))
                .images(asList("four_cheeses.jpg"))
                .build());
        products.add(Product.builder()
                .name("Mario")
                .price(new Double(80))
                .weight(550)
                .category(PIZZA)
                .description("Thin crust. Tomato sauce, basil, olive oil, mayonnaise, mozzarella cheese, chicken, cheese Dor Blue, spinach, cottage sausage, peppers.")
                .ingredients(asList("tomato sauce", "basil", "olive oil", "mayonnaise", "mozzarella cheese", "chicken", "cheese Dor Blue", "spinach", "cottage sausage", "peppers"))
                .images(asList("mario.jpg", "mario.jpg"))
                .build());
        products.add(Product.builder()
                .name("Rancho")
                .price(new Double(80))
                .weight(580)
                .category(PIZZA)
                .description("Mozzarella cheese, chicken fillet, red pepper, marinated / fresh mushrooms, mayonnaise, pizza sauce, basil, olive oil")
                .ingredients(asList("mozzarella cheese", "chicken fillet", "red pepper", "mushrooms", "mayonnaise", "pizza sauce", "basil", "olive oil"))
                .images(asList("rancho.jpg"))
                .build());
        products.add(Product.builder()
                .name("Diablo")
                .price(new Double(80))
                .weight(520)
                .category(PIZZA)
                .description("Mozzarella cheese, sausages cabanos and ostrova,pepperoni, hot pepper, gogoshare, fresh mushrooms, green olives, salami, pizza sauce, basil, olive oil")
                .ingredients(asList("mozzarella cheese", "sausages cabanos and ostrova", "pepperoni", "hot pepper", "gogoshare", "salami", "mayonnaise", "pizza sauce", "basil", "olive oil"))
                .images(asList("diablo.jpg", "diablo.jpg"))
                .build());
        return products;
    }

    private static List<Product> computeSalads() {
        return asList(
                Product.builder()
                        .name("Chicken caesar")
                        .price(new Double(70))
                        .category(SALADS)
                        .weight(300)
                        .description("Iceberg lettuce, spinach,caesar dressing, baked chicken, croutons, cherry tomatoes, parmigiano cheese.")
                        .ingredients(asList("iceberg lettuce", "spinach", "caesar dressing", "baked chicken", "croutons", "cherry tomatoes", "parmigiano cheese"))
                        .images(asList("caesar.jpg"))
                        .build(),
                Product.builder()
                        .name("Greek")
                        .price(new Double(60))
                        .category(SALADS)
                        .weight(300)
                        .description("Tomatoes, cucumbers, bell peppers, red onions, kalamata olives, cheese, olive oil, lemon juice, oregano, basil.")
                        .ingredients(asList("tomatoes", "cucumbers", "bell peppers", "red onions", "kalamata olives", "cheese", "olive oil", "lemon juice", "oregano", "basil"))
                        .images(asList("greek.jpg", "greek.jpg"))
                        .build(),
                Product.builder()
                        .name("Veggy")
                        .price(new Double(55))
                        .category(SALADS)
                        .weight(120)
                        .description("Rucola, iceberg salad, lollo salad, caramelized walnut, almond flakes, olive oil, balsamic vinegar, dry berry.")
                        .ingredients(asList("rucola", "iceberg salad", "lollo salad", "caramelized walnut", "almond flakes", "olive oil", "balsamic vinegar", "dry berry"))
                        .images(asList("veggy.jpg", "veggy.jpg"))
                        .build()
        );
    }

    private static List<Product> computeDesserts() {
        return asList(
                Product.builder()
                        .name("Cheesecake")
                        .price(new Double(40))
                        .weight(200)
                        .category(DESSERT)
                        .description("Delicious dessert with a delicate and light taste based on soft cream cheeses with cherries.")
                        .images(asList("dessert.jpg", "dessert.jpg"))
                        .build(),
                Product.builder()
                        .name("Fruit Salad")
                        .price(new Double(35))
                        .weight(270)
                        .category(DESSERT)
                        .description("Mix fruit with kiwi, bananas, oranges, peaches, pineapple with whipped cream and syrup \"grenadine\".")
                        .images(asList("fruit_salad.jpg", "fruit_salad.jpg"))
                        .build(),
                Product.builder()
                        .name("Tiramisu")
                        .price(new Double(40))
                        .weight(120)
                        .category(DESSERT)
                        .description("Classic italian dessert based on cream cheese mascarpone and Amaretto liquer with biscuits savoiardi impregnated with coffee.")
                        .images(asList("tiramisu.jpg"))
                        .build(),
                Product.builder()
                        .name("Chocolate plombir 13%")
                        .price(new Double(55))
                        .weight(700)
                        .category(DESSERT)
                        .description("Delicious dessert with a delicate and light taste based on soft cream cheeses with cherries.")
                        .images(asList("plombir.jpg"))
                        .build());
    }

    private static List<Product> computeDrinks() {
        return asList(
                Product.builder()
                        .name("Banana-Mango milkshake")
                        .price(new Double(37))
                        .volume(330)
                        .category(DRINK)
                        .description("Ice cream, mango, banana.")
                        .images(asList("milkshake.jpg"))
                        .build(),
                Product.builder()
                        .name("Orange fresh")
                        .price(new Double(30))
                        .volume(200)
                        .category(DRINK)
                        .description("Orange fresh")
                        .images(asList("fresh.jpg"))
                        .build(),
                Product.builder()
                        .name("Cappucino")
                        .price(new Double(37))
                        .volume(300)
                        .category(DRINK)
                        .description("Espresso with milk cream")
                        .images(asList("cappucino.jpg"))
                        .build(),
                Product.builder()
                        .name("Velkopopovicky Kozel Cerny")
                        .price(new Double(37))
                        .volume(500)
                        .category(DRINK)
                        .description("Velkopopovicky Kozel Cerny 0.5L")
                        .images(asList("kozel.jpg"))
                        .build()
                );
    }
}
