package pl.marcin.przymus.spring5recipeapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.marcin.przymus.spring5recipeapp.repositories.CategoryRepository;
import pl.marcin.przymus.spring5recipeapp.repositories.UnitOfMeasureRepository;

@Controller
@Slf4j
public class IndexController {
    //@RequestMapping({"", "/", "/index"})
    String getIndexPage() {
        log.debug("Getting index page");
        return "index";
    }
}
