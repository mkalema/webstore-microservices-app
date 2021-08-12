package webshop.orders.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import webshop.ProductRecord;
import webshop.orders.domain.Order;
import webshop.shoppingcarts.domain.ShoppingCart;

@Service
public class Sender {

    @Autowired
    private KafkaTemplate<String, ProductRecord> kafkaTemplate;

    public void send(String topic, ProductRecord productRecord){
        kafkaTemplate.send(topic, productRecord);
    }
}
