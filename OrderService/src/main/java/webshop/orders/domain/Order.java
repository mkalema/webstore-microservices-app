package webshop.orders.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import webshop.shoppingcarts.domain.Customer;

import java.util.ArrayList;

@Document
public class Order {

    @Id
    private Integer orderId;
    private ArrayList<OrderLine> orderLines = new ArrayList<>();
    private double totalPrice;
    private Customer customer;

    public Order() {
    }

    public Order(Integer orderId, ArrayList<OrderLine> orderLines, double totalPrice, Customer customer) {
        this.orderId = orderId;
        this.orderLines = orderLines;
        this.totalPrice = totalPrice;
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderLines=" + orderLines +
                ", totalPrice=" + totalPrice +
                ", customer=" + customer +
                '}';
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public ArrayList<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(ArrayList<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
