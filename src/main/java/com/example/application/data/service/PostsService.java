package com.example.application.data.service;

import com.example.application.data.entity.Post;
import com.example.application.data.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostsService {
    private final PostRepository postRepository;

    public PostsService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAllPosts(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return postRepository.findAll();
        } else {
            return postRepository.search(stringFilter);
        }
    }

    public long countPost() {
        return postRepository.count();
    }

    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    public void savePost(Post post) {
        if (post == null) {
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        postRepository.save(post);
    }


}
