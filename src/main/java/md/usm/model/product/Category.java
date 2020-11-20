package md.usm.model.product;

public enum Category {
    PIZZA,
    SALADS,
    DESSERT,
    DRINK;

    public static Category get(final String category) {
        return valueOf(category.toUpperCase());
    }
}
