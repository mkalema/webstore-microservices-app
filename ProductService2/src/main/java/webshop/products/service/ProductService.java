package webshop.products.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webshop.products.data.Product;
import webshop.products.domain.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product save(Product product){
        return productRepository.save(product);
    }

    public Product update(String productNumber, Product product){
        product.setProductNumber(productNumber);

        return productRepository.save(product);
    }

    public void delete(String productNumber){
        productRepository.deleteById(productNumber);
    }

    public Product get(String productNumber){
        return productRepository.findById(productNumber).orElse(null);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Integer getNumberInStock(String productNumber){
        Product product = productRepository.findById(productNumber).orElse(null);
        return product != null ? product.getNumberInStock() : 0;
    }
}
