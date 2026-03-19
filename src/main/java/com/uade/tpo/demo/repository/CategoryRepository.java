package com.uade.tpo.demo.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import com.uade.tpo.demo.entity.Category;

public class CategoryRepository {
    private ArrayList<Category> categories;

    public CategoryRepository() {
        categories = new ArrayList<Category>(
                Arrays.asList(Category.builder().description("Artes").id(1).build(),
                        Category.builder().description("Ciencia").id(2).build(),
                        Category.builder().description("Fantasia").id(3).build(),
                        Category.builder().description("Terror").id(4).build()));
    }

    public ArrayList<Category> getCategories() {
        return this.categories;
    }

    public Optional<Category> getCategoryById(int categoryId) {
        return this.categories.stream().filter(m -> m.getId() == categoryId).findAny();
    }

    public Category createCategory(int newCategoryId, String description) {
        Category newCategory = Category.builder()
                .description(description)
                .id(newCategoryId).build();
        this.categories.add(newCategory);
        return newCategory;
    }
}
