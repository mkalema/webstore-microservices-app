package webshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerReplica1Application {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerReplica1Application.class, args);
    }

}
