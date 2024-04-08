package com.blueyonder.shopdataservice.repositories;

import com.blueyonder.shopdataservice.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findByName(String name);
    List<Product> findAll();
    List<Product> findByValue(Double query);
    List<Product> findByDescriptionContaining(String query);
    List<Product> findByNameContaining(String name);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByDescriptionContainingIgnoreCase(String query);
}
