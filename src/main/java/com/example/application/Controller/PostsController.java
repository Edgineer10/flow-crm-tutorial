package com.example.application.Controller;

import com.example.application.data.entity.Post;
import com.example.application.data.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@RestController
public class PostsController {
    @Autowired
    private PostRepository PostRep;
    @CrossOrigin
    @GetMapping("/getposts")
    public List<Post> getpost(){
        List<Post> posts = PostRep.findAll();
        posts.sort(Comparator.comparing(Post::getPostDate).reversed());
        return posts;
    }


}
