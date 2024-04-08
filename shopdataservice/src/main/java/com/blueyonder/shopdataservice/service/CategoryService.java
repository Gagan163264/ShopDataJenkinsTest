package com.blueyonder.shopdataservice.service;

import com.blueyonder.shopdataservice.Exceptions.CategoryNotFoundException;
import com.blueyonder.shopdataservice.Exceptions.TooManyCharactersException;
import com.blueyonder.shopdataservice.entities.Category;

import java.util.List;

public interface CategoryService {
    public Category addCategory(Category category) throws TooManyCharactersException;
    public Category updateCategory(Category category) throws TooManyCharactersException;
    public void deleteCategory(Integer id) throws CategoryNotFoundException;
    public Category getCategoryById(Integer id) throws CategoryNotFoundException;
    public List<Category> getCategoryByName(String name) throws CategoryNotFoundException;
    public List<Category> getAllCategories() throws CategoryNotFoundException;
    public List<Category> getCategoriesByDescription(String query) throws CategoryNotFoundException;
}
