package pl.marcin.przymus.spring5recipeapp.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.marcin.przymus.spring5recipeapp.services.RecipeService;

@Slf4j
@Controller
public class RecipesController {
    private final RecipeService recipeService;

    public RecipesController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping({"", "/", "/index"})
    public String showRecipes(Model model) {
        log.debug("Getting recipes page");
        model.addAttribute("recipes", recipeService.getRecipes());
        return "recipes";
    }
}
