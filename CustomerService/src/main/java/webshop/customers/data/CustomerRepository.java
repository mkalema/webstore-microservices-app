package webshop.customers.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import webshop.customers.domain.Customer;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, Integer> {
}
