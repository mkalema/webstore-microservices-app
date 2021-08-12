package webshop.customers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webshop.customers.data.CustomerRepository;
import webshop.customers.domain.Customer;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer save(Customer customer){
        Integer count = this.getAllCustomers().size();

        if(count <= 0){
            count = 1;
        }
        customer.setId(count++);

        return customerRepository.save(customer);
    }

    public Customer update(Integer id, Customer customer){
        customer.setId(id);

        return customerRepository.save(customer);
    }

    public void delete(Integer id){
        customerRepository.deleteById(id);
    }

    public Customer get(Integer id){
        return customerRepository.findById(id).orElse(null);
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }
}
