package webshop.shoppingcarts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import webshop.shoppingcarts.data.ShoppingCartRepository;
import webshop.shoppingcarts.domain.ShoppingCart;

@Service
public class Receiver {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @KafkaListener(topics = {"newShoppingCartTopic" , "updatedShoppingCartTopic", "deletedShoppingCartTopic"})
    public void receive(@Payload ShoppingCart shoppingCart,
                        @Headers MessageHeaders headers) {
        System.out.println("received message="+ shoppingCart.toString() + " Topic: " + headers.get(KafkaHeaders.RECEIVED_TOPIC));

        if(headers.get(KafkaHeaders.RECEIVED_TOPIC).equals("newShoppingCartTopic")){

            shoppingCartRepository.save(shoppingCart);

        }else if(headers.get(KafkaHeaders.RECEIVED_TOPIC).equals("updatedShoppingCartTopic")){

            shoppingCartRepository.save(shoppingCart);

        }else if(headers.get(KafkaHeaders.RECEIVED_TOPIC).equals("deletedShoppingCartTopic")){
            shoppingCartRepository.deleteById(shoppingCart.getCartId());
        }else{
            System.out.println("received message="+ shoppingCart.toString() + " Topic: " + headers.get(KafkaHeaders.RECEIVED_TOPIC));
        }
    }
}
