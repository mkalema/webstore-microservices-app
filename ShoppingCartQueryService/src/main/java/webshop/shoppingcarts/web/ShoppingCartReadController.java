package webshop.shoppingcarts.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webshop.CustomError;
import webshop.shoppingcarts.domain.CartLine;
import webshop.shoppingcarts.domain.ShoppingCart;
import webshop.shoppingcarts.service.ShoppingCartReadService;

@RestController
public class ShoppingCartReadController {

    @Autowired
    private ShoppingCartReadService shoppingCartReadService;

    @GetMapping("/shoppingcartqueries/{cartId}")
    public ResponseEntity<?> get(@PathVariable String cartId){
        ShoppingCart shoppingCart = shoppingCartReadService.get(cartId);

        if(shoppingCart == null){
            return new ResponseEntity<>(new CustomError("Shopping Cart with cart id " +
                    cartId + " does not exist"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(shoppingCart, HttpStatus.OK);
    }

}
