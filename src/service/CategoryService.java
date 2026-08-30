package service;

import model.Category;
import repository.CategoryRepository;

import java.util.List;

public class CategoryService {

    private CategoryRepository repository =
            new CategoryRepository();

    public List<Category> getAllCategories() {

        return repository.getAllCategories();

    }

    public void addCategory(Category category) {

        category.setId(repository.getNextId());

        repository.addCategory(category);

    }

    public Category findById(int id) {

        return repository.findById(id);

    }

    public Category findByName(String name) {

        return repository.findByName(name);

    }

}