package de.thkoeln.web.today.controller;

import de.thkoeln.web.today.Config;
import de.thkoeln.web.today.models.Post;
import de.thkoeln.web.today.models.PostRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private Config config;

    @GetMapping("/images/{imageFilepath}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String imageFilepath) throws FileNotFoundException {
        Path path = Paths.get(config.imagePath, imageFilepath);
        File image = new File(path.toString());
        MediaType contentType;

        // Security check to avoid leaving the image directory.
        if (!path.startsWith(config.imagePath)) {
            return ResponseEntity.notFound().build();
        }

        // Check the content type of the image.
        if (path.toString().endsWith(".png")) {
            contentType = MediaType.IMAGE_PNG;
        } else if (path.toString().endsWith(".jpeg")) {
            contentType = MediaType.IMAGE_JPEG;
        } else {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(contentType)
                .body(new InputStreamResource(new FileInputStream(image)));
    }

    @PostMapping("/upload-post")
    public String uploadPost(@RequestPart("sunshineDuration") String sunshineDuration, @RequestPart("title") String title, @RequestPart("text") String text, @RequestPart("image") MultipartFile image, RedirectAttributes redirectAttrs) {
        // Check if a png or jpeg image has been uploaded.
        String contentType = image.getContentType();
        String imageExtension;
        if (contentType.equals("image/png")) {
            imageExtension = "png";
        } else if (contentType.equals("image/jpeg")) {
            imageExtension = "jpeg";
        } else {
            redirectAttrs.addFlashAttribute("message", "Upload fehlgeschlagen: Ungültiges Bild");
            return "redirect:/upload";
        }

        // Generate a unique name for the image and save it to disk.
        String imageFilename = UUID.randomUUID().toString() + "." + imageExtension;
        try {
            // Ensure the image directory exists.
            File imageDirectory = new File(config.imagePath);
            if (!imageDirectory.exists()) {
                imageDirectory.mkdirs();
            }

            // Write the file to disk.
            Path imagePath = Paths.get(config.getImagePath(), imageFilename);
            Files.write(imagePath, image.getBytes());
        } catch (IOException e) {
            redirectAttrs.addFlashAttribute("message", "Upload fehlgeschlagen: Schreiben fehlgeschlagen");
            return "redirect:/upload";
        }

        // Save the post in the database.
        Post newPost = new Post();
        newPost.setSunshineDuration(Integer.parseInt(sunshineDuration));
        newPost.setTitle(title);
        newPost.setText(text);
        newPost.setImage("/images/" + imageFilename);
        newPost.setCreatedAt(LocalDate.from(LocalDate.now()));
        postRepository.save(newPost);

        redirectAttrs.addFlashAttribute("message", "Upload erfolgreich: " + title);
        return "redirect:/upload";
    }

}
