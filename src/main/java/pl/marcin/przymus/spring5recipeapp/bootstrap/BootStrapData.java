package pl.marcin.przymus.spring5recipeapp.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.marcin.przymus.spring5recipeapp.domain.*;
import pl.marcin.przymus.spring5recipeapp.repositories.CategoryRepository;
import pl.marcin.przymus.spring5recipeapp.repositories.RecipeRepository;
import pl.marcin.przymus.spring5recipeapp.repositories.UnitOfMeasureRepository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


@Slf4j
@Component
@RequiredArgsConstructor
public class BootStrapData implements ApplicationListener<ContextRefreshedEvent> {
    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Set<Category> categories = new HashSet<>();
        log.debug("creating mexican category");
        Category mexicanCategory = categoryRepository.findByDescription("Mexican").orElseThrow(IllegalArgumentException::new);
        categories.add(mexicanCategory);
        Set<Ingredient> ingredients = new HashSet<>();
        log.debug("Creating guacamole");
        Recipe guacamole = new Recipe();
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(guacamole);
        mexicanCategory.setRecipes(recipes);
        guacamole.setUrl("http://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamole.setSource("Simply Recipes");
        guacamole.setCategories(categories);
        guacamole.setDescription("Perfect Guacamole");
        guacamole.setCookTime(0);
        guacamole.setPrepTime(10);
        guacamole.setDifficulty(Difficulty.EASY);
        guacamole.setServings(3);
        guacamole.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd");
        log.debug("Adding note do guacamole");
        Notes notes = new Notes();
        notes.setRecipeNotes("The best guacamole keeps it simple: just ripe avocados, salt, a squeeze of lime, onions, chiles, cilantro, and some chopped tomato. Serve it as a dip at your next party or spoon it on top of tacos for an easy dinner upgrade. ");
        guacamole.setNotes(notes);
        log.debug("Creating avocado ingredient");
        Ingredient avocado = new Ingredient();
        avocado.setDescription("avocado");
        avocado.setRecipe(guacamole);
        avocado.setAmount(BigDecimal.valueOf(2));
        avocado.setUnitOfMeasure(unitOfMeasureRepository.findByDescription("Piece").orElseThrow(IllegalArgumentException::new));
        avocado.setDescription("ripe");
        ingredients.add(avocado);
        log.debug("Creating salt ingredient");
        Ingredient salt = new Ingredient();
        salt.setDescription("salt");
        salt.setRecipe(guacamole);
        salt.setAmount(BigDecimal.valueOf(0.25));
        salt.setUnitOfMeasure(unitOfMeasureRepository.findByDescription("Teaspoon").orElseThrow(IllegalArgumentException::new));
        ingredients.add(salt);
        log.debug("Adding ingredients to guacamole");
        guacamole.setIngredients(ingredients);
        recipeRepository.save(guacamole);
        categoryRepository.save(mexicanCategory);
    }
}
