package repository;

import model.Category;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {

    private static final String FILE_PATH = "data/categories.txt";

    public List<Category> getAllCategories() {

        List<Category> categories = new ArrayList<>();

        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return categories;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");

                Category category = new Category(
                        Integer.parseInt(data[0]),
                        data[1]
                );

                categories.add(category);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return categories;
    }

    public void addCategory(Category category) {

        List<Category> categories = getAllCategories();

        categories.add(category);

        saveAllCategories(categories);
    }

    public Category findById(int id) {

        List<Category> categories = getAllCategories();

        for (Category category : categories) {

            if (category.getId() == id) {
                return category;
            }

        }

        return null;
    }

    public Category findByName(String name) {

        List<Category> categories = getAllCategories();

        for (Category category : categories) {

            if (category.getName().equalsIgnoreCase(name)) {
                return category;
            }

        }

        return null;
    }

    public void updateCategory(Category updatedCategory) {

        List<Category> categories = getAllCategories();

        for (int i = 0; i < categories.size(); i++) {

            if (categories.get(i).getId() == updatedCategory.getId()) {

                categories.set(i, updatedCategory);

                break;
            }

        }

        saveAllCategories(categories);
    }

    public void deleteCategory(int id) {

        List<Category> categories = getAllCategories();

        categories.removeIf(category -> category.getId() == id);

        saveAllCategories(categories);
    }

    private void saveAllCategories(List<Category> categories) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(FILE_PATH))) {

            for (Category category : categories) {

                writer.write(
                        category.getId() + "," +
                                category.getName()
                );

                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int getNextId() {

        int max = 0;

        List<Category> categories = getAllCategories();

        for(Category category : categories){

            if(category.getId() > max){

                max = category.getId();

            }

        }

        return max + 1;

    }
}