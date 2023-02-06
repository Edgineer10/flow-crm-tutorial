package com.example.application.data.repository;


import com.example.application.data.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p " +
            "where lower(p.postTitle) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(p.postDesc) like lower(concat('%', :searchTerm, '%'))")

    List<Post> search(@Param("searchTerm") String searchTerm);
}
