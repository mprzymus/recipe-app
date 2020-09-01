package pl.marcin.przymus.spring5recipeapp.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.marcin.przymus.spring5recipeapp.domain.Recipe;
import pl.marcin.przymus.spring5recipeapp.repositories.RecipeRepository;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    @Override
    public void saveImageFile(Long id, MultipartFile file) {
        try {
            log.debug("saving image for recipe: " + id);
            Recipe recipe = recipeRepository.findById(id).get();
            Byte[] imageBytes = new Byte[file.getBytes().length];
            int i = 0;
            for (byte b : file.getBytes()) {
                imageBytes[i++] = b;
            }
            recipe.setImage(imageBytes);
            recipeRepository.save(recipe);
        } catch (IOException e) {
            log.error("Error occurred", e);
            e.printStackTrace();
        }
    }
}
