package webshop.orders.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import webshop.orders.domain.Order;
import webshop.orders.domain.OrderLine;
import webshop.shoppingcarts.domain.CartLine;
import webshop.shoppingcarts.domain.ShoppingCart;

import java.util.ArrayList;
import java.util.List;

@Service
public class Receiver {

    @Autowired
    private OrderService orderService;

    @KafkaListener(topics = {"checkOutOrderTopic" })
    public void receive(@Payload ShoppingCart shoppingCart,
                        @Headers MessageHeaders headers) {
        System.out.println("received message="+ shoppingCart.toString() + " Topic: " + headers.get(KafkaHeaders.RECEIVED_TOPIC));

        if(headers.get(KafkaHeaders.RECEIVED_TOPIC).equals("checkOutOrderTopic")){

            Order order = new Order();
            ArrayList<OrderLine> orderLines = new ArrayList<>();
            double totalPrice = 0.0;

            for(CartLine cartLine : shoppingCart.getCartLines()){
                OrderLine orderLine = new OrderLine();
                orderLine.setProduct(cartLine.getProduct());
                orderLine.getProduct().setNumberInStock(null);
                orderLine.setQuantity(cartLine.getQuantity());
                orderLines.add(orderLine);
                totalPrice += cartLine.getProduct().getPrice() * cartLine.getQuantity();
            }

            order.setOrderLines(orderLines);
            order.setTotalPrice(totalPrice);

            // generate id
            List<Order> orders =  orderService.getAll();
            order.setOrderId(orders.size() + 1);
            orderService.add(order);

        }else{
            System.out.println(" Topic: " + headers.get(KafkaHeaders.RECEIVED_TOPIC));
        }
    }
}
