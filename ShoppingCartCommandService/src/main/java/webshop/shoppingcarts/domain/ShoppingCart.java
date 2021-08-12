package webshop.shoppingcarts.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Document
public class ShoppingCart {

    @Id
    private String cartId;
    private ArrayList<CartLine> cartLines = new ArrayList<>();

    public ShoppingCart() {
    }

    public ShoppingCart(String cartId, ArrayList<CartLine> cartLines) {
        this.cartId = cartId;
        this.cartLines = cartLines;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "cartId='" + cartId + '\'' +
                ", cartLines=" + cartLines +
                '}';
    }

    public void addProduct(Product product, Integer quantity){
        for (CartLine cline : cartLines) {
            if (cline.getProduct().getProductNumber().equals(product.getProductNumber())) {
                cline.setQuantity(cline.getQuantity()+quantity);
                return;
            }
        }
        CartLine cline = new CartLine();
        cline.setProduct(product);
        cline.setQuantity(quantity);
        cartLines.add(cline);
    }

    public void removeFromCart(Product product){
        Iterator<CartLine> iter = cartLines.iterator();
        while (iter.hasNext()){
            CartLine cline = iter.next();
            if (cline.getProduct().getProductNumber().equals(product.getProductNumber())){
                if (cline.getQuantity()>1){
                    cline.setQuantity(cline.getQuantity()-1);
                }
                else{
                    iter.remove();
                }
            }
        }
    }

    public void changeQuantity(Product product, Integer quantity){
        Iterator<CartLine> iter = cartLines.iterator();
        while (iter.hasNext()){
            CartLine cline = iter.next();
            if (cline.getProduct().getProductNumber().equals(product.getProductNumber())){
                cline.setQuantity(quantity);
            }
        }
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public List<CartLine> getCartLines() {
        return cartLines;
    }

    public void setCartLines(ArrayList<CartLine> cartLines) {
        this.cartLines = cartLines;
    }
}
