import java.util.List;
import java.util.ArrayList;

class Admin {
    private String username;
    private String password;
    private List<Product> products;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
        this.products = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void addProduct(Product product) throws InvalidPriceException {
        if (product.getPrice() <= 0) {
            throw new InvalidPriceException("Preço inválido. O preço deve ser maior que zero.");
        }
        products.add(product);
        System.out.println("Produto adicionado: " + product.getName());
    }

    public void removeProduct(String productName) throws ProductNotFoundException {
        boolean found = false;
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                products.remove(product);
                System.out.println("Produto removido: " + productName);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new ProductNotFoundException("Produto não encontrado: " + productName);
        }
    }

    public void editProduct(String productName, double newPrice) throws InvalidPriceException, ProductNotFoundException {
        if (newPrice <= 0) {
            throw new InvalidPriceException("Preço inválido. O preço deve ser maior que zero.");
        }
        boolean found = false;
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                product.setPrice(newPrice);
                System.out.println("Produto editado: " + productName);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new ProductNotFoundException("Produto não encontrado: " + productName);
        }
    }

    public void listProducts() {
        System.out.println("Produtos cadastrados:");
        for (Product product : products) {
            System.out.println(product.getName() + " - R$" + product.getPrice());
        }
    }
}
