package webshop.orders.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import webshop.orders.domain.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, Integer> {
}
