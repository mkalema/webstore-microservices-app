package webshop.customers.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webshop.CustomError;
import webshop.customers.domain.Customer;
import webshop.customers.service.CustomerService;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class.getName());

    @PostMapping("/customers")
    public ResponseEntity<?> add(@RequestBody Customer customer){

        Customer customer1 = customerService.save(customer);
        logger.debug("added customer + " + customer1);

        return new ResponseEntity<>(customer1, HttpStatus.OK);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id){
        Customer customer = customerService.get(id);

        if(customer == null){
            logger.debug("Customer with id " + id + " does not exist");
            return new ResponseEntity<>(new CustomError("Customer with id " +
                    id + " does not exist"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Customer customer){
        Customer customer1 = customerService.get(id);

        if(customer1 == null){
            logger.debug("Customer with id " + id + " does not exist");
            return new ResponseEntity<>(new CustomError("Customer with id " +
                    id + " does not exist"), HttpStatus.NOT_FOUND);
        }

        customer = customerService.update(id,customer);

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        Customer customer = customerService.get(id);

        if(customer == null){
            logger.debug("Customer with id " + id + " does not exist");
            return new ResponseEntity<>(new CustomError("Customer with id " +
                    id + " does not exist"), HttpStatus.NOT_FOUND);
        }
        customerService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
