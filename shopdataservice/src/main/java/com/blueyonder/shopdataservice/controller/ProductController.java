package com.blueyonder.shopdataservice.controller;

import com.blueyonder.shopdataservice.Exceptions.CategoryNotFoundException;
import com.blueyonder.shopdataservice.Exceptions.ProductNotFoundException;
import com.blueyonder.shopdataservice.Exceptions.TooManyCharactersException;
import com.blueyonder.shopdataservice.entities.Category;
import com.blueyonder.shopdataservice.entities.Product;
import com.blueyonder.shopdataservice.service.CategoryService;
import com.blueyonder.shopdataservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Set;

@RestController
@RequestMapping("/ecapp/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/addproduct")
    public ResponseEntity<Product> add_product(@RequestBody Product product) throws TooManyCharactersException {
        Product prod = productService.addProduct(product);
        return new ResponseEntity<>(prod, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Product> update_product(@RequestBody Product product) throws ProductNotFoundException, TooManyCharactersException {
        Product prod = productService.getProductById(product.getId());
        if(product.getName() != null)
            prod.setName(product.getName());
        if(product.getDescription() != null)
            prod.setDescription(product.getDescription());
        if(product.getValue() != null)
            prod.setValue(product.getValue());
        Product p = productService.updateProduct(prod);
        return new ResponseEntity<>(p, HttpStatus.OK);

    }

    @DeleteMapping("/delete")
    public ResponseEntity<Product> delete_product(@RequestParam Integer id) throws ProductNotFoundException {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private HashMap<String, String> getMp(String error){
        HashMap<String, String> hmp = new HashMap<>();
        hmp.put("message", error);
        return hmp;
    }
    @GetMapping(value = "/getproduct", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> get_product(@RequestParam String field, @RequestParam String value) throws ProductNotFoundException {
        return switch (field) {
            case "id" ->
                    new ResponseEntity<>(productService.getProductById(Integer.parseInt(value)), HttpStatus.OK);
            case "name" -> new ResponseEntity<>(productService.getProductByName(value), HttpStatus.OK);
            case "value" ->
                    new ResponseEntity<>(productService.getProductsByValue(Double.parseDouble(value)), HttpStatus.OK);
            case "description" ->
                    new ResponseEntity<>(productService.getProductsByDescription(value), HttpStatus.OK);
            default -> new ResponseEntity<>(getMp("Invalid field"), HttpStatus.BAD_REQUEST);
        };
    }

    @GetMapping("/getallproducts")
    public ResponseEntity<Iterable<Product>> get_all_products() throws ProductNotFoundException {
        System.out.println("lalala");
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @PostMapping("/linkproductTocategory")
    public ResponseEntity<Object> link_product_category(@RequestParam Integer productId, @RequestParam Integer categoryId) throws ProductNotFoundException, CategoryNotFoundException, TooManyCharactersException {
        StringBuilder sb = new StringBuilder();
        HashMap<String, String> jsonObject = new HashMap<>();
        Product product = productService.getProductById(productId);
        Set<Category> categories = product.getCategory_lst();
        Category cat = categoryService.getCategoryById(categoryId);
        categories.add(cat);
        product.setCategory_lst(categories);
        productService.updateProduct(product);
        sb.append("Added ").append(product.getName()).append(" to ").append(cat.getName());
        jsonObject.put("message",sb.toString());
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

    @PostMapping("/unlinkproductFromcategory")
    public ResponseEntity<Object> unlink_product_category(@RequestParam Integer productId, @RequestParam Integer categoryId) throws ProductNotFoundException, CategoryNotFoundException, TooManyCharactersException {
        StringBuilder sb = new StringBuilder();
        HashMap<String, String> jsonObject = new HashMap<>();
        Product product = productService.getProductById(productId);
        Set<Category> categories = product.getCategory_lst();
        Category cat = categoryService.getCategoryById(categoryId);
        if(!categories.remove(cat)){
            throw new ProductNotFoundException("Product not in category");
        }
        product.setCategory_lst(categories);
        productService.updateProduct(product);
        sb.append("Removed ").append(product.getName()).append(" from ").append(cat.getName());
        jsonObject.put("message",sb.toString());
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

    @GetMapping("/help")
    public ResponseEntity<Object> help(){
        return new ResponseEntity<>(getMp("No help lmao"), HttpStatus.OK);
    }

}
