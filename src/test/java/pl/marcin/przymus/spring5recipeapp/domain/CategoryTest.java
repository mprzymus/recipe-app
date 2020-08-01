package pl.marcin.przymus.spring5recipeapp.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
    }

    @Test
    void getId() {
        category.setId(41L);
        assertEquals(category.getId(), 41L);
    }

    @Test
    void getDescription() {
    }

    @Test
    void getRecipes() {
    }
}