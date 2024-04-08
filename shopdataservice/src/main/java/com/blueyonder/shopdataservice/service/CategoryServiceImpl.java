package com.blueyonder.shopdataservice.service;

import com.blueyonder.shopdataservice.Exceptions.CategoryNotFoundException;
import com.blueyonder.shopdataservice.Exceptions.TooManyCharactersException;
import com.blueyonder.shopdataservice.entities.Category;
import com.blueyonder.shopdataservice.repositories.CategoryRepository;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository catRepo;

    public Category addCategory(Category category) throws TooManyCharactersException {
        try {
            return catRepo.save(category);
        }catch (DataException e){
            throw new TooManyCharactersException("Too many characters, max size is "+Category.DESCRIPTION_LENGTH,e);
        }
    }

    public Category updateCategory(Category category) throws TooManyCharactersException {
        try {
            return catRepo.save(category);
        }catch (Exception e){
            throw new TooManyCharactersException("Too many characters, max size is "+Category.DESCRIPTION_LENGTH, e);
        }
    }

    public void deleteCategory(Integer id) throws CategoryNotFoundException {
        if(!catRepo.existsById(id))
            throw new CategoryNotFoundException("Category not found with id: " + id);
        catRepo.deleteById(id);
    }

    public Category getCategoryById(Integer id) throws CategoryNotFoundException {
        Optional<Category> res = catRepo.findById(id);
        if(res.isPresent()) {
            return res.get();
        }
        else {
            throw new CategoryNotFoundException("Category not found with id: " + id);
        }
    }

    public List<Category> getCategoryByName(String name) throws CategoryNotFoundException {
        List<Category> res = catRepo.findByNameContainingIgnoreCase(name);
        if(res.isEmpty()){
            throw new CategoryNotFoundException("Category not found with name: " + name);
        }
        return res;
    }

    public List<Category> getAllCategories() throws CategoryNotFoundException {
        List<Category> res = catRepo.findAll();
        if(res.isEmpty())
            throw new CategoryNotFoundException("No Categories found");
        else
            return res;
    }

    public List<Category> getCategoriesByDescription(String query) throws CategoryNotFoundException {
        List<Category> res = catRepo.findByDescriptionContainingIgnoreCase(query);
        if(res.isEmpty())
            throw new CategoryNotFoundException("No Categories found");
        else
            return res;
    }

}
