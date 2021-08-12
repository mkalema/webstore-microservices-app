package webshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import webshop.client.domain.*;

import java.util.ArrayList;

@SpringBootApplication
public class WebShopClientApplication implements CommandLineRunner {

    @Autowired
    private RestOperations restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(WebShopClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String serverUrl = "http://localhost:8080/products";

        // add Product1
        restTemplate.postForLocation(serverUrl, new Product("301","Phone", 100,
                "Latest Phone", 25));
        // get
        Product product1 = restTemplate.getForObject(serverUrl+"/{productNumber}", Product.class, "301");
        System.out.println("----------- get Product1-----------------------");
        System.out.println(product1);

        // add Product2
        restTemplate.postForLocation(serverUrl, new Product("302","Watch", 50,
                "Rolex", 10));
        // get
        Product product2 = restTemplate.getForObject(serverUrl+"/{productNumber}", Product.class, "302");
        System.out.println("----------- get Product2-----------------------");
        System.out.println(product2);

        // add Product3
        restTemplate.postForLocation(serverUrl, new Product("303","Laptop", 800,
                "HP Spectre", 10));
        // get
        Product product3 = restTemplate.getForObject(serverUrl+"/{productNumber}", Product.class, "303");
        System.out.println("----------- get Product3-----------------------");
        System.out.println(product3);

        // =========== Modify Product3 ============================================== //
        product3.setName("HP Laptop");
        product3.setNumberInStock(7);
        restTemplate.put(serverUrl+"/{productNumber}", product3, "303");

        product3 = restTemplate.getForObject(serverUrl+"/{productNumber}", Product.class, "303");
        System.out.println("----------- get Product3 modified-----------------------");
        System.out.println(product3);

        // ======== Create ShoppingCart ================ //
        System.out.println("----------- Create ShoppingCart -----------------------");
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCartId("001");

        serverUrl = "http://localhost:8080/shoppingcartcommands";
        restTemplate.postForLocation(serverUrl, shoppingCart);
        shoppingCart = restTemplate.getForObject(serverUrl+"/{cartId}", ShoppingCart.class, "001");
        System.out.println("----------- get ShoppingCart-----------------------");
        System.out.println(shoppingCart);

        ArrayList<CartLine> cartlineList = new ArrayList<>();

        CartLine cartLine1 = new CartLine(2, product1);
        cartlineList.add(cartLine1);
        CartLine cartLine2 = new CartLine(5, product2);
        cartlineList.add(cartLine2);
        CartLine cartLine3 = new CartLine(1, product3);
        cartlineList.add(cartLine3);

        //shoppingCart.setCartLines(cartlineList);

        System.out.println("----------- Add Products to ShoppingCart -----------------------");
        restTemplate.postForLocation(serverUrl + "/addProduct" + "/{cartId}", cartLine1, "001");
        restTemplate.postForLocation(serverUrl + "/addProduct" + "/{cartId}", cartLine2, "001");
        restTemplate.postForLocation(serverUrl + "/addProduct" + "/{cartId}", cartLine3, "001");
        shoppingCart = restTemplate.getForObject(serverUrl+"/{cartId}", ShoppingCart.class, "001");
        System.out.println("----------- get ShoppingCart-----------------------");
        System.out.println(shoppingCart);

        System.out.println("----------- Remove Product from ShoppingCart -----------------------");
        restTemplate.postForLocation(serverUrl + "/removeProduct" + "/{cartId}", cartLine3, "001");
        shoppingCart = restTemplate.getForObject(serverUrl+"/{cartId}", ShoppingCart.class, "001");
        System.out.println("----------- get ShoppingCart-----------------------");
        System.out.println(shoppingCart);

        System.out.println("----------- Change Quantity of Product 2 from ShoppingCart -----------------------");
        cartLine2.setQuantity(3);
        restTemplate.postForLocation(serverUrl + "/changeQuantity" + "/{cartId}", cartLine2, "001");
        shoppingCart = restTemplate.getForObject(serverUrl+"/{cartId}", ShoppingCart.class, "001");
        System.out.println("----------- get ShoppingCart-----------------------");
        System.out.println(shoppingCart);

        System.out.println("----------- Checkout ShoppingCart -----------------------");
        restTemplate.postForLocation(serverUrl + "/checkout" + "/{cartId}", cartLine2, "001");

        Customer customer = new Customer();

        customer.setFirstName("Moses");
        customer.setLastName("Kalema");
        customer.setAddress(new Address("4th N Street", "Fairfield", "52557"));
        customer.setContact(new Contact("6542389012", "mkalema@miu.edu"));

        System.out.println("----------- First Create Customer -----------------------");//customers
        serverUrl = "http://localhost:8080/customers";
        customer = restTemplate.postForObject(serverUrl , customer, Customer.class);
        System.out.println("----------- get Customer-----------------------");
        System.out.println(customer);

        System.out.println("----------- Add Customer to Order -----------------------");
        serverUrl = "http://localhost:8080/orders";
        Order order = restTemplate.getForObject(serverUrl+"/{orderId}", Order.class, "1");
        System.out.println("----------- get Order-----------------------");
        System.out.println(order);

        System.out.println("----------- Place Order -----------------------");
        customer = new Customer(customer.getId(), customer.getFirstName(), customer.getLastName(), null, null);

        order.setCustomer(customer);
        order = restTemplate.postForObject(serverUrl + "/placeOrder" + "/{orderId}" , customer, Order.class, "1");
        System.out.println(order);

    }

    @Bean
    RestOperations restTemplate() {
        return new RestTemplate();
    }
}
