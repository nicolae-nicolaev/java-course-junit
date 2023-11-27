package com.endava.javacoursejunit.service;

import com.endava.javacoursejunit.domain.Post;
import com.endava.javacoursejunit.repository.PostRepository;

import java.util.List;
import java.util.NoSuchElementException;

public class PostService {

    private PostRepository postRepository;

    public Post getPostById(Integer postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("No posts with specified id found!"));
    }

    public List<Post> getPostsByUserId(Integer userId) {
        return postRepository.findPostsByUserId(userId);
    }

    public void savePost(Post post) {
        postRepository.save(post);
    }
}
