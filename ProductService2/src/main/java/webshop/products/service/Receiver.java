package webshop.products.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import webshop.ProductRecord;
import webshop.products.data.Product;

@Service
public class Receiver {

    @Autowired
    private ProductService productService;

    @KafkaListener(topics = {"orderPlacedTopic" })
    public void receive(@Payload ProductRecord productRecord,
                        @Headers MessageHeaders headers) {
        System.out.println("received message="+ productRecord.toString() + " Topic: " + headers.get(KafkaHeaders.RECEIVED_TOPIC));

        if(headers.get(KafkaHeaders.RECEIVED_TOPIC).equals("orderPlacedTopic")){

            Product product = productService.get(productRecord.getProductNumber());

            product.setNumberInStock(product.getNumberInStock() - productRecord.getQuantity());
            product = productService.update(product.getProductNumber(), product);

            System.out.println("Product with product number " + product.getProductNumber()
                    + " number in stock has been updated to " + product.getNumberInStock());

        }else{
            System.out.println(" Topic: " + headers.get(KafkaHeaders.RECEIVED_TOPIC));
        }
    }
}
