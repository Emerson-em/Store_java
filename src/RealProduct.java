public class RealProduct extends Product{
    public RealProduct(String name, double price){
        super(name, price);
    }

    @Override
    public void showDetails(){
        System.out.println("Produto: " + getName());
        System.out.println("Preço: " + getPrice() + "R$");
    }
}
