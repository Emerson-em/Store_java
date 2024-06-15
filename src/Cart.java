import java.util.List;
abstract class Cart {
    public abstract void addProduct(Product product);

    public abstract List<Product> productsList();

    public abstract double totalPrice();
}
