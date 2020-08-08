package pl.marcin.przymus.spring5recipeapp.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.marcin.przymus.spring5recipeapp.repositories.CategoryRepository;
import pl.marcin.przymus.spring5recipeapp.repositories.UnitOfMeasureRepository;
import pl.marcin.przymus.spring5recipeapp.services.RecipeService;

@RequiredArgsConstructor
@Controller
@Slf4j
public class IndexController {
    private final RecipeService recipeService;

    @GetMapping({"", "/", "/index"})
    public String showRecipes(Model model) {
        log.debug("Getting recipes page");
        model.addAttribute("recipes", recipeService.getRecipes());
        return "recipes";
    }
}
