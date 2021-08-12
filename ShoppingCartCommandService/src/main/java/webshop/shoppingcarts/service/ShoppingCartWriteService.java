package webshop.shoppingcarts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webshop.shoppingcarts.Sender;
import webshop.shoppingcarts.data.ShoppingCartRepository;
import webshop.shoppingcarts.domain.CartLine;
import webshop.shoppingcarts.domain.Product;
import webshop.shoppingcarts.domain.ShoppingCart;

import java.util.List;

@Service
public class ShoppingCartWriteService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private Sender sender;

    public ShoppingCart add(ShoppingCart shoppingCart){
        shoppingCart = shoppingCartRepository.save(shoppingCart);

        sender.send("newShoppingCartTopic", shoppingCart);

        return shoppingCart;
    }

    public ShoppingCart get(String cartId){
        return shoppingCartRepository.findById(cartId).orElse(null);
    }

    public ShoppingCart update(String cartId, ShoppingCart shoppingCart){
        shoppingCart.setCartId(cartId);
        shoppingCart = shoppingCartRepository.save(shoppingCart);

        sender.send("updatedShoppingCartTopic", shoppingCart);
        return shoppingCart;
    }

    public void delete(String cartId){

        ShoppingCart cart = this.get(cartId);

        shoppingCartRepository.deleteById(cartId);

        sender.send("deletedShoppingCartTopic", cart);
    }

    public List<ShoppingCart> getAll(){
        return shoppingCartRepository.findAll();
    }

    public ShoppingCart addProduct(String cartId,CartLine cartLine){
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId).get();

        shoppingCart.addProduct(cartLine.getProduct(),  cartLine.getQuantity());

        shoppingCart = shoppingCartRepository.save(shoppingCart);

        sender.send("updatedShoppingCartTopic", shoppingCart);

        return shoppingCart;
    }

    public ShoppingCart removeProduct(String cartId, Product product){
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId).get();

        shoppingCart.removeFromCart(product);

        shoppingCart = shoppingCartRepository.save(shoppingCart);

        sender.send("updatedShoppingCartTopic", shoppingCart);

        return shoppingCart;
    }

    public ShoppingCart changeQuantity(String cartId, CartLine cartLine){
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId).get();


        shoppingCart.changeQuantity(cartLine.getProduct(), cartLine.getQuantity());
        shoppingCart = shoppingCartRepository.save(shoppingCart);

        sender.send("updatedShoppingCartTopic", shoppingCart);

        return shoppingCart;
    }

    public void checkOut(String cartId){
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId).get();

        // push event to kafka
        sender.send("checkOutOrderTopic", shoppingCart);

    }
}
