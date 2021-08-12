package webshop.orders.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webshop.orders.data.OrderRepository;
import webshop.orders.domain.Order;
import webshop.shoppingcarts.domain.ShoppingCart;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    //@Autowired
    //private Sender sender;

    public Order add(Order order){
        order = orderRepository.save(order);

        //sender.send("newShoppingCartTopic", shoppingCart);

        return order;
    }

    public Order get(Integer orderId){
        return orderRepository.findById(orderId).orElse(null);
    }

    public Order update(Integer orderId, Order order){
        order.setOrderId(orderId);
        order = orderRepository.save(order);

        //sender.send("updatedShoppingCartTopic", shoppingCart);
        return order;
    }

    public void delete(Integer orderId){

        Order order = this.get(orderId);

        orderRepository.deleteById(orderId);

        //sender.send("deletedShoppingCartTopic", cart);
    }

    public List<Order> getAll(){
        return orderRepository.findAll();
    }



}
