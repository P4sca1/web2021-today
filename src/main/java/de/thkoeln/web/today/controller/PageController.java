package de.thkoeln.web.today.controller;

import de.thkoeln.web.today.models.Post;
import de.thkoeln.web.today.models.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.NoSuchElementException;

@Controller
public class PageController {

  @Autowired
  PostRepository postRepository;

  @GetMapping("/")
  public String getIndexPage(Model model) {
    model.addAttribute("posts", postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")));

    return "index";
  }

  @GetMapping("/upload")
  public String getUploadPostPage() {
    return "upload-post";
  }

  @GetMapping("/posts/{id}")
  public String getPostPage(Model model, @PathVariable Long id) {
    try {
      Post post = postRepository.findById(id).orElseThrow();
      model.addAttribute("post", post);

      return "post";
    } catch (NoSuchElementException e) {
      return "404";
    }

  }

  @GetMapping("/404")
  public String getNotFoundPage() {
    return "404";
  }

}
