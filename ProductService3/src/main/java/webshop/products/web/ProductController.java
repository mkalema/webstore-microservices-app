package webshop.products.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webshop.CustomError;
import webshop.products.data.Product;
import webshop.products.service.ProductService;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<?> add(@RequestBody Product product){
        Product product1 = productService.save(product);

        return new ResponseEntity<>(product1, HttpStatus.OK);
    }

    @GetMapping("/products/{productNumber}")
    public ResponseEntity<?> get(@PathVariable String productNumber){
        Product product = productService.get(productNumber);

        if(product == null){
            return new ResponseEntity<>(new CustomError("Product with product number " +
                    productNumber + " does not exist"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/products/{productNumber}")
    public ResponseEntity<?> update(@PathVariable String productNumber, @RequestBody Product product){
        Product product1 = productService.get(productNumber);

        if(product1 == null){
            return new ResponseEntity<>(new CustomError("Product with product number " +
                    productNumber + " does not exist"), HttpStatus.NOT_FOUND);
        }

        product = productService.update(productNumber,product);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/products/{productNumber}")
    public ResponseEntity<?> delete(@PathVariable String productNumber){
        Product product = productService.get(productNumber);

        if(product == null){
            return new ResponseEntity<>(new CustomError("Product with product number " +
                    productNumber + " does not exist"), HttpStatus.NOT_FOUND);
        }
        productService.delete(productNumber);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/products/stock/{productNumber}")
    public ResponseEntity<?> getNumberInStock(@PathVariable String productNumber){
        Product product = productService.get(productNumber);

        if(product == null){
            return new ResponseEntity<>(new CustomError("Product with product number " +
                    productNumber + " does not exist"), HttpStatus.NOT_FOUND);
        }

        Integer numberInStock = productService.getNumberInStock(productNumber);

        return new ResponseEntity<>(numberInStock, HttpStatus.OK);
    }

}
