abstract class User implements Payment{
    private String name;
    private String password;

    public String getName(){
        return name;
    }
    public String getPassword(){
        return password;
    }
    @Override
    public boolean paymentProcess(double price){
        System.out.println("O pagamento de "+ price + "R$ está sendo processado");
        return true;
    }
    public abstract void addToCart(Product product);
    public abstract Cart getCart();
}
