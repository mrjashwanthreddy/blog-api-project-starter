package com.embarkx.blogapi;

import java.time.LocalDateTime;
import java.util.UUID;

public class Post {

    private final UUID id;
    private String title;
    private String content;
    private final LocalDateTime createdAt;

    public Post(String title, String content) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
