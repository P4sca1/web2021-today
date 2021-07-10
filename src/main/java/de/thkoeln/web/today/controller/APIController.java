package de.thkoeln.web.today.controller;

import de.thkoeln.web.today.models.Post;
import de.thkoeln.web.today.models.PostRepository;
import de.thkoeln.web.today.models.StatisticEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v1")
public class APIController {

  @Autowired
  private PostRepository postRepository;

  @GetMapping("/posts")
  public Iterable<Post> getAllPosts() {
    return postRepository.findAll();
  }

  @GetMapping("/posts/{id}")
  public Post findOnePost(@PathVariable Long id) {
    return postRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find post"));
  }

  @GetMapping("/statistics")
  public List<StatisticEntry> getStatistics() {
    List<StatisticEntry> statistics = new ArrayList<>();

    for (Post post : postRepository.findAll()) {
      statistics.add(new StatisticEntry(post.getCreatedAt(), post.getSunshineDuration()));
    }

    return statistics;
  }

}
