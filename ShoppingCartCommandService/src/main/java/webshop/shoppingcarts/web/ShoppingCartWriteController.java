package webshop.shoppingcarts.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webshop.CustomError;
import webshop.shoppingcarts.domain.CartLine;
import webshop.shoppingcarts.domain.ShoppingCart;
import webshop.shoppingcarts.service.ShoppingCartWriteService;

@RestController
public class ShoppingCartWriteController {

    @Autowired
    private ShoppingCartWriteService shoppingCartWriteService;

    @Autowired
    private StockFeignClient stockFeignClient;

    @PostMapping("/shoppingcartcommands")
    public ResponseEntity<?> add(@RequestBody ShoppingCart shoppingCart){
        ShoppingCart cart = shoppingCartWriteService.add(shoppingCart);

        return new ResponseEntity<>(cart, HttpStatus.OK);

    }

    @GetMapping("/shoppingcartcommands/{cartId}")
    public ResponseEntity<?> get(@PathVariable String cartId){
        ShoppingCart shoppingCart = shoppingCartWriteService.get(cartId);

        if(shoppingCart == null){
            return new ResponseEntity<>(new CustomError("Shopping Cart with cart id " +
                    cartId + " does not exist"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(shoppingCart, HttpStatus.OK);
    }

    @PutMapping("/shoppingcartcommands/{cartId}")
    public ResponseEntity<?> update(@PathVariable String cartId, @RequestBody ShoppingCart shoppingCart){
        ShoppingCart shoppingCart1 = shoppingCartWriteService.get(cartId);

        if(shoppingCart1 == null){
            return new ResponseEntity<>(new CustomError("Shopping Cart with cart id " +
                    cartId + " does not exist"), HttpStatus.NOT_FOUND);
        }

        shoppingCart = shoppingCartWriteService.update(cartId,shoppingCart);

        return new ResponseEntity<>(shoppingCart, HttpStatus.OK);
    }

    @DeleteMapping("/shoppingcartcommands/{cartId}")
    public ResponseEntity<?> delete(@PathVariable String cartId){
        ShoppingCart shoppingCart = shoppingCartWriteService.get(cartId);

        if(shoppingCart == null){
            return new ResponseEntity<>(new CustomError("Shopping Cart with cart id " +
                    cartId + " does not exist"), HttpStatus.NOT_FOUND);
        }
        shoppingCartWriteService.delete(cartId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/shoppingcartcommands/addProduct/{cartId}")
    public ResponseEntity<?> addProduct(@PathVariable String cartId, @RequestBody CartLine cartLine){
        ShoppingCart shoppingCart = shoppingCartWriteService.get(cartId);

        if(shoppingCart == null){
            return new ResponseEntity<>(new CustomError("Shopping Cart with cart id " +
                    cartId + " does not exist"), HttpStatus.NOT_FOUND);
        }

        Integer numberInStock = stockFeignClient.getStock(cartLine.getProduct().getProductNumber());

        if(cartLine.getQuantity() > numberInStock){
            return new ResponseEntity<>(new CustomError("Product  with product number " +
                    cartLine.getProduct().getProductNumber() + " has "
                    + numberInStock + " in stock which is less than " + cartLine.getQuantity()), HttpStatus.NOT_FOUND);
        }

        shoppingCart = shoppingCartWriteService.addProduct(cartId, cartLine);

        return new ResponseEntity<>(shoppingCart, HttpStatus.OK);
    }

    @PostMapping("/shoppingcartcommands/removeProduct/{cartId}")
    public ResponseEntity<?> removeProduct(@PathVariable String cartId, @RequestBody CartLine cartLine){
        ShoppingCart shoppingCart = shoppingCartWriteService.get(cartId);

        if(shoppingCart == null){
            return new ResponseEntity<>(new CustomError("Shopping Cart with cart id " +
                    cartId + " does not exist"), HttpStatus.NOT_FOUND);
        }

        shoppingCart = shoppingCartWriteService.removeProduct(cartId, cartLine.getProduct());

        return new ResponseEntity<>(shoppingCart, HttpStatus.OK);
    }

    @PostMapping("/shoppingcartcommands/changeQuantity/{cartId}")
    public ResponseEntity<?> changeQuantity(@PathVariable String cartId, @RequestBody CartLine cartLine){
        ShoppingCart shoppingCart = shoppingCartWriteService.get(cartId);

        if(shoppingCart == null){
            return new ResponseEntity<>(new CustomError("Shopping Cart with cart id " +
                    cartId + " does not exist"), HttpStatus.NOT_FOUND);
        }

        Integer numberInStock = stockFeignClient.getStock(cartLine.getProduct().getProductNumber());

        if(cartLine.getQuantity() > numberInStock){
            return new ResponseEntity<>(new CustomError("Product  with product number " +
                    cartLine.getProduct().getProductNumber() + " has "
                    + numberInStock + " in stock which is less than " + cartLine.getQuantity()), HttpStatus.NOT_FOUND);
        }

        shoppingCart = shoppingCartWriteService.changeQuantity(cartId, cartLine);

        return new ResponseEntity<>(shoppingCart, HttpStatus.OK);
    }

    @GetMapping("/shoppingcartcommands/quantity/{cartId}")
    public ResponseEntity<?> getQuantity(@PathVariable String cartId, @RequestBody CartLine cartLine){
        ShoppingCart shoppingCart = shoppingCartWriteService.get(cartId);

        if(shoppingCart == null){
            return new ResponseEntity<>(new CustomError("Shopping Cart with cart id " +
                    cartId + " does not exist"), HttpStatus.NOT_FOUND);
        }

        Integer numberInStock = stockFeignClient.getStock(cartLine.getProduct().getProductNumber());

        return new ResponseEntity<>(numberInStock, HttpStatus.OK);
    }

    @PostMapping("/shoppingcartcommands/checkout/{cartId}")
    public ResponseEntity<?> checkOut(@PathVariable String cartId){
        ShoppingCart shoppingCart = shoppingCartWriteService.get(cartId);

        if(shoppingCart == null){
            return new ResponseEntity<>(new CustomError("Shopping Cart with cart id " +
                    cartId + " does not exist"), HttpStatus.NOT_FOUND);
        }

        shoppingCartWriteService.checkOut(cartId);

        return new ResponseEntity<>(shoppingCart, HttpStatus.OK);
    }

    @FeignClient(name = "ProductService")
    interface StockFeignClient{

        @GetMapping("/products/stock/{productNumber}")
        public int getStock(@PathVariable String productNumber);

    }

}
