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
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/ecapp/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @PostMapping("/addcategory")
    public ResponseEntity<Category> add_cat(@RequestBody Category category) throws TooManyCharactersException {
        Category cat = categoryService.addCategory(category);
        return new ResponseEntity<>(cat, HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update_cat(@RequestBody Category category) throws CategoryNotFoundException, TooManyCharactersException {
        Category cat = categoryService.getCategoryById(category.getId());
        if(category.getName() != null)
            cat.setName(category.getName());
        if(category.getDescription() != null)
            cat.setDescription(category.getDescription());
        Category ct = categoryService.updateCategory(cat);
        return new ResponseEntity<>(ct, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Category> delete_cat(@RequestParam Integer id) throws CategoryNotFoundException {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private HashMap<String, String> getMp(String error){
        HashMap<String, String> hmp = new HashMap<>();
        hmp.put("message", error);
        return hmp;
    }
    @GetMapping(value = "/getcategory", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> get_cat(@RequestParam String field, @RequestParam String value) throws CategoryNotFoundException {
        return switch(field){
            case "id" -> new ResponseEntity<>(categoryService.getCategoryById(Integer.parseInt(value)), HttpStatus.OK);
            case "name" -> new ResponseEntity<>(categoryService.getCategoryByName(value), HttpStatus.OK);
            case "description" -> new ResponseEntity<>(categoryService.getCategoriesByDescription(value), HttpStatus.OK);
            default -> new ResponseEntity<>(getMp("Invalid field"), HttpStatus.BAD_REQUEST);
        };
    }

    @GetMapping("/getallcategories")
    public ResponseEntity<List<Category>> get_all_cat() throws CategoryNotFoundException {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @PostMapping("/unlinkproduct")
    public ResponseEntity<Object> unlink_product(@RequestParam Integer productId, @RequestParam Integer categoryId) throws CategoryNotFoundException, ProductNotFoundException, TooManyCharactersException {
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
    
}
