package webshop.orders.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webshop.CustomError;
import webshop.ProductRecord;
import webshop.orders.domain.Order;
import webshop.orders.domain.OrderLine;
import webshop.orders.service.OrderService;
import webshop.orders.service.Sender;
import webshop.shoppingcarts.domain.Customer;

@RestController
public class OrderController {

    @Autowired
    private Sender sender;

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<?> add(@RequestBody Order order){
        Order order1 = orderService.add(order);

        return new ResponseEntity<>(order1, HttpStatus.OK);

    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<?> get(@PathVariable Integer orderId){
        Order order = orderService.get(orderId);

        if(order == null){
            return new ResponseEntity<>(new CustomError("Order with order id " +
                    orderId + " does not exist"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<?> update(@PathVariable Integer orderId, @RequestBody Order order){
        Order order1 = orderService.get(orderId);

        if(order1 == null){
            return new ResponseEntity<>(new CustomError("Order with order id " +
                    orderId + " does not exist"), HttpStatus.NOT_FOUND);
        }

        order = orderService.update(orderId,order);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<?> delete(@PathVariable Integer orderId){
        Order order = orderService.get(orderId);

        if(order == null){
            return new ResponseEntity<>(new CustomError("Order with order id " +
                    orderId + " does not exist"), HttpStatus.NOT_FOUND);
        }
        orderService.delete(orderId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/orders/placeOrder/{orderId}")
    public ResponseEntity<?> placeOrder(@PathVariable Integer orderId, @RequestBody Customer customer){
        Order order = orderService.get(orderId);

        if(order == null){
            return new ResponseEntity<>(new CustomError("Order with order id " +
                    orderId + " does not exist"), HttpStatus.NOT_FOUND);
        }

        order.setCustomer(customer);

        order = orderService.update(orderId, order);

        // send event to update products
        for(OrderLine orderLine: order.getOrderLines()){
            sender.send("orderPlacedTopic", new ProductRecord(orderLine.getProduct().getProductNumber(),
                    orderLine.getQuantity()));
        }

        // send cutstomer email
        System.out.println("Email: Order for customer " + customer.getFirstName() + " "
                + customer.getLastName() +  " has been placed");

        return new ResponseEntity<>(order, HttpStatus.OK);

    }

}
