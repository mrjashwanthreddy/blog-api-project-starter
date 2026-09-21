package com.embarkx.blogapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class BlogController {

    private static final List<Post> posts = new ArrayList<>();

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestParam String title, @RequestParam String content) {
        Post post = new Post(title, content);
        posts.add(post);
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return posts;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable UUID id) {
        return posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/validate")
    public String validateContent(@RequestParam String content) {
        if (content.length() > 5000) {
            return "Too long";
        }
        return "OK";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable UUID id) {
        boolean removed = posts.removeIf(p -> p.getId().equals(id));
        if (removed) {
            return ResponseEntity.ok("Deleted");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public List<Post> searchPosts(@RequestParam String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return posts.stream()
                .filter(p -> p.getTitle().toLowerCase().contains(lowerKeyword))
                .collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/total")
    public String getTotalWordCount() {
        List<String> wordCounts = List.of("100", "200", "300");
        String total = "";
        for (String count : wordCounts) {
            total += count;
        }
        return "Total words: " + total;
    }
}