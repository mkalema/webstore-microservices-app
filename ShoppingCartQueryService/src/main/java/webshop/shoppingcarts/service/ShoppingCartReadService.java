package webshop.shoppingcarts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webshop.shoppingcarts.data.ShoppingCartRepository;
import webshop.shoppingcarts.domain.CartLine;
import webshop.shoppingcarts.domain.Product;
import webshop.shoppingcarts.domain.ShoppingCart;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartReadService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public ShoppingCart get(String cartId){
        return shoppingCartRepository.findById(cartId).orElse(null);
    }

    public List<ShoppingCart> getAll(){
        return shoppingCartRepository.findAll();
    }

}
