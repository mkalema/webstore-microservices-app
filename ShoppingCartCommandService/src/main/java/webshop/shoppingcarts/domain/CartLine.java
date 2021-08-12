package webshop.shoppingcarts.domain;

public class CartLine {

    private Integer quantity;
    private Product product;

    public CartLine() {
    }

    public CartLine(Integer quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    @Override
    public String toString() {
        return "CartLine{" +
                "quantity=" + quantity +
                ", product=" + product +
                '}';
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
