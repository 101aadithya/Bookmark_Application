package com.example.myapp.Models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 100)
    private String title;

    @Column(length = 300)
    private String url;
    
    @ManyToOne
    @JoinColumn(name = "user_id")			//new
    private User user;
    
    @Column(length = 100)
    @CreationTimestamp
    private LocalDateTime createdAt;    

    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public LocalDateTime getCreatedAt() {
    	return createdAt;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}