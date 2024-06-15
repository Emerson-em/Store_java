public class RegisteredClient extends User implements Payment, Report{
    private String name;
    private String password;

    public RegisteredClient(String name, String password) {
        this.name = name;
        this.password = password;
    }

    private Cart carrinho = new RealCart();

    @Override
    public void addToCart(Product product){
        getCart().addProduct(product);
    }

    @Override
    public Cart getCart(){
        return carrinho;
    }

    @Override
    public boolean paymentProcess(double price){
        System.out.println("O pagamento de "+ price + "R$ está sendo processado");
        return true;
    }

    @Override
    public void generateReport(){
        System.out.println("Relatório do seu carrinho de compras: ");
        for (Product product : carrinho.productsList()){
            System.out.println("Produto: "+ product.getName()+", Preço: "+product.getPrice());
        }
    }
}
