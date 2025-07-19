package com.example.myapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.myapp.Models.Bookmark;
import com.example.myapp.Models.User;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {
    @Query("SELECT p FROM Bookmark p WHERE p.title LIKE %:keyword% OR p.url LIKE %:keyword%")
    List<Bookmark> findAllByKeyword(@Param("keyword") String keyword);
    
    int countByUser(User user);			//new
    
}