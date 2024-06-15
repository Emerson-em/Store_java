import java.util.List;
import java.util.ArrayList;
public class RealCart extends Cart implements Discount{
    private List<Product> products = new ArrayList<>();

    @Override
    public void addProduct(Product product){
        products.add(product);
    }

    @Override
    public List<Product> productsList() {
        return products;
    }

    @Override
    public double totalPrice() {
        double total = 0;
        for (Product product : products){
            total += product.getPrice();
        }
        return total;
    }

    @Override
    public double applyDiscount(double price) {
        return price * 0.9;
    }
}
