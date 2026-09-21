package com.embarkx.blogapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    /**
     * Returns all posts whose title contains the given keyword,
     * case-insensitive. Maps to:
     *   SELECT * FROM blog_posts WHERE LOWER(title) LIKE LOWER('%keyword%')
     */
    List<Post> findByTitleContainingIgnoreCase(String keyword);

    /**
     * Returns true if at least one post with the given title exists,
     * case-insensitive. Maps to:
     *   SELECT COUNT(*) > 0 FROM blog_posts WHERE LOWER(title) = LOWER(title)
     */
    boolean existsByTitleIgnoreCase(String title);
}
