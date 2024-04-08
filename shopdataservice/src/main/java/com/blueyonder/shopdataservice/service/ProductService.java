package com.blueyonder.shopdataservice.service;

import com.blueyonder.shopdataservice.Exceptions.ProductNotFoundException;
import com.blueyonder.shopdataservice.Exceptions.TooManyCharactersException;
import com.blueyonder.shopdataservice.entities.Product;

import java.util.List;

public interface ProductService {
    public Product addProduct(Product product) throws TooManyCharactersException;
    public Product updateProduct(Product product) throws TooManyCharactersException;
    public void deleteProduct(Integer id) throws ProductNotFoundException;
    public Product getProductById(Integer id) throws ProductNotFoundException;
    public List<Product> getProductByName(String name) throws ProductNotFoundException;
    public List<Product> getAllProducts() throws ProductNotFoundException;
    public List<Product> getProductsByValue(Double query) throws ProductNotFoundException;
    public List<Product> getProductsByDescription(String query) throws ProductNotFoundException;
}
