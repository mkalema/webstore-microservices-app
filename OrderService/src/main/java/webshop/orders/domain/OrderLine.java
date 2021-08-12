package webshop.orders.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import webshop.shoppingcarts.domain.Product;

@Document
public class OrderLine {

    private Integer quantity;
    private Product product;

    public OrderLine() {
    }

    public OrderLine(Integer quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    @Override
    public String toString() {
        return "OrderLine{" +
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
