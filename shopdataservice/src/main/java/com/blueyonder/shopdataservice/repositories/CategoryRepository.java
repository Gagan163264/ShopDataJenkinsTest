package com.blueyonder.shopdataservice.repositories;

import com.blueyonder.shopdataservice.entities.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
    List<Category> findByName(String name);
    List<Category> findAll();
    List<Category> findByDescriptionContaining(String query);
    List<Category> findByNameContaining(String name);

    List<Category> findByNameContainingIgnoreCase(String name);

    List<Category> findByDescriptionContainingIgnoreCase(String query);
}
