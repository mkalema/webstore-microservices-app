package webshop.shoppingcarts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import webshop.shoppingcarts.domain.ShoppingCart;

@Service
public class Sender {

    @Autowired
    private KafkaTemplate<String, ShoppingCart> kafkaTemplate;

    public void send(String topic, ShoppingCart shoppingCart){
        kafkaTemplate.send(topic, shoppingCart);
    }
}
