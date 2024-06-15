public class NotRegisteredClient extends User implements Payment, Report{
    private Cart carrinho2 = new RealCart();

    @Override
    public void addToCart(Product product){
        getCart().addProduct(product);
    }

    @Override
    public Cart getCart(){
        return carrinho2;
    }

    @Override
    public boolean paymentProcess(double price){
        System.out.println("O pagamento de "+ price + "R$ está sendo processado");
        return true;
    }

    @Override
    public void generateReport(){
        System.out.println("Relatório do seu carrinho de compras: ");
        for (Product product : carrinho2.productsList()){
            System.out.println("Produto: "+ product.getName()+", Preço: "+product.getPrice());
        }
    }
}
