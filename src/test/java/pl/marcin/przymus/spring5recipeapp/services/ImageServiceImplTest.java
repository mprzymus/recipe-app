package pl.marcin.przymus.spring5recipeapp.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pl.marcin.przymus.spring5recipeapp.domain.Recipe;
import pl.marcin.przymus.spring5recipeapp.repositories.RecipeRepository;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class ImageServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;
    @InjectMocks
    private ImageServiceImpl imageService;

    @Test
    void saveImageFile() throws IOException {
        Long id = 1L;
        MultipartFile multipartFile = new MockMultipartFile
                ("imagefile", "testing.txt", "text/plain", "Some text".getBytes());
        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        imageService.saveImageFile(id, multipartFile);

        verify(recipeRepository).save(argumentCaptor.capture());
        Recipe result = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, result.getImage().length);

    }
}