package pl.marcin.przymus.spring5recipeapp.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.marcin.przymus.spring5recipeapp.commands.RecipeCommand;
import pl.marcin.przymus.spring5recipeapp.services.ImageService;
import pl.marcin.przymus.spring5recipeapp.services.RecipeService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService imageService;

    @GetMapping("recipe/{recipeId}/image")
    public String showUploadForm(@PathVariable Long recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(recipeId));
        return "recipe/imageuploadform";
    }

    @PostMapping("recipe/{recipeId}/image")
    public String handleImagePost(@PathVariable Long recipeId, @RequestParam("imagefile") MultipartFile file) {
        imageService.saveImageFile(recipeId, file);

        return "redirect:/recipe/" + recipeId + "/show";
    }

    @GetMapping("recipe/{recipeId}/recipeimage")
    public void renderImageFromDB(@PathVariable Long recipeId, HttpServletResponse response) throws IOException {
        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);
        byte[] bytesArray = new byte[recipeCommand.getImage().length];
        int i = 0;
        for (byte wrappedByte : recipeCommand.getImage()) {
            bytesArray[i++] = wrappedByte;
        }
        response.setContentType("image/jpeg");
        InputStream is = new ByteArrayInputStream(bytesArray);
        IOUtils.copy(is, response.getOutputStream());
    }
}
