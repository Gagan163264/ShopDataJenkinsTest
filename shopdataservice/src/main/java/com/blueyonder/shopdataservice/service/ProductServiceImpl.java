package com.blueyonder.shopdataservice.service;

import com.blueyonder.shopdataservice.Exceptions.ProductNotFoundException;
import com.blueyonder.shopdataservice.Exceptions.TooManyCharactersException;
import com.blueyonder.shopdataservice.entities.Category;
import com.blueyonder.shopdataservice.entities.Product;
import com.blueyonder.shopdataservice.repositories.ProductRepository;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product) throws TooManyCharactersException {
        try {
            return productRepository.save(product);
        }catch (Exception e){
            throw new TooManyCharactersException("Too many characters, max size is "+ Product.DESCRIPTION_LENGTH,e);
        }
    }

    public Product updateProduct(Product product) throws TooManyCharactersException {
        try {
            return productRepository.save(product);
        }catch (DataException e){
            throw new TooManyCharactersException("Too many characters, max size is "+ Product.DESCRIPTION_LENGTH,e);
        }
    }

    public void deleteProduct(Integer id) throws ProductNotFoundException {
        if(!productRepository.existsById(id))
            throw new ProductNotFoundException("Product not found with id: " + id);
        productRepository.deleteById(id);
    }

    public Product getProductById(Integer id) throws ProductNotFoundException {
        Optional<Product> res = productRepository.findById(id);
        if(res.isPresent()) {
            return res.get();
        }
        else {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
    }

    public List<Product> getProductByName(String name) throws ProductNotFoundException {
        List<Product> res = productRepository.findByNameContainingIgnoreCase(name);
        if(!res.isEmpty()){
            return res;
        }
        else {
            throw new ProductNotFoundException("Product not found with name: " + name);
        }
    }

    public List<Product> getAllProducts() throws ProductNotFoundException {
        List<Product> res = productRepository.findAll();
        if(res.isEmpty())
            throw new ProductNotFoundException("No products found");
        else
            return res;
    }

    public List<Product> getProductsByValue(Double query) throws ProductNotFoundException {
        List<Product> res = productRepository.findByValue(query);
        if(res.isEmpty())
            throw new ProductNotFoundException("No products found");
        else
            return res;
    }

    public List<Product> getProductsByDescription(String query) throws ProductNotFoundException {
        List<Product> res = productRepository.findByDescriptionContainingIgnoreCase(query);;
        if(res.isEmpty())
            throw new ProductNotFoundException("No products found");
        else
            return res;
    }
}
