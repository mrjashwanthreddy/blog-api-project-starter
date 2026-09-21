package com.embarkx.blogapi;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    private static final int TITLE_MIN = 3;
    private static final int TITLE_MAX = 100;
    private static final int CONTENT_MIN = 50;
    private static final int CONTENT_MAX = 5000;

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public Post createPost(String title, String content) {
        validateTitle(title);
        validateContent(content);
        return postRepository.save(new Post(title, content));
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(UUID id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
    }

    public Post updatePost(UUID id, String title, String content) {
        validateTitle(title);
        validateContent(content);

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));

        post.setTitle(title);
        post.setContent(content);
        return postRepository.save(post);
    }

    public void deletePost(UUID id) {
        if (!postRepository.existsById(id)) {
            throw new PostNotFoundException(id.toString());
        }
        postRepository.deleteById(id);
    }

    public List<Post> searchByTitle(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Search keyword must not be blank");
        }
        return postRepository.findByTitleContainingIgnoreCase(keyword);
    }

    // -------------------------------------------------------------------------
    // Validation helpers
    // -------------------------------------------------------------------------

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title must not be blank");
        }
        int len = title.trim().length();
        if (len < TITLE_MIN || len > TITLE_MAX) {
            throw new IllegalArgumentException(
                    String.format("Title must be between %d and %d characters (got %d)", TITLE_MIN, TITLE_MAX, len));
        }
    }

    private void validateContent(String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content must not be blank");
        }
        int len = content.trim().length();
        if (len < CONTENT_MIN || len > CONTENT_MAX) {
            throw new IllegalArgumentException(
                    String.format("Content must be between %d and %d characters (got %d)", CONTENT_MIN, CONTENT_MAX, len));
        }
    }
}
