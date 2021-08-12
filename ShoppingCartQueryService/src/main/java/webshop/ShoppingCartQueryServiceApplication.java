package webshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@EnableDiscoveryClient
public class ShoppingCartQueryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingCartQueryServiceApplication.class, args);
    }

}
