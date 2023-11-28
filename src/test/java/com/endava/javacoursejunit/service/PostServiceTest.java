package com.endava.javacoursejunit.service;

import com.endava.javacoursejunit.domain.Post;
import com.endava.javacoursejunit.domain.User;

import com.endava.javacoursejunit.repository.PostRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private static final Post mockPost = new Post(
            "Lorem Ipsum",
            """
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean nec neque elementum mauris
                    dignissim euismod. Vestibulum vel aliquam lorem, mollis placerat tellus. Donec sodales blandit
                    lorem, nec ultrices orci vehicula tristique. Donec augue sem, rhoncus et iaculis nec, posuere a
                    tortor. Sed eu justo sed erat sollicitudin vehicula at at magna. Ut ut leo ac lorem gravida congue.
                    Praesent ligula ante, malesuada at risus ut, malesuada maximus ex. Ut lacinia urna a tellus
                    eleifend, in rhoncus lorem varius. Nunc condimentum felis id nunc convallis, ac ultricies libero
                    fermentum. Vestibulum vel tortor eu ipsum accumsan laoreet. Etiam tempor eros sit amet est
                    ultricies mollis. Quisque nec euismod elit. Nunc ac felis faucibus, lobortis orci vitae,
                    elementum turpis.
            """
    );

    private static final User mockUser = new User("mockuser");

    @BeforeAll
    static void setup() {
        mockPost.setUser(mockUser);
    }

    @Test
    void shouldGetPostById() {
        when(postRepository.findById(any(Integer.class))).thenReturn(Optional.of(mockPost));

        Post actual = postService.getPostById(1);

        assertEquals(mockPost.getTitle(), actual.getTitle(), "Post title should be \"Lorem Ipsum\"");
        assertEquals(mockPost.getContent(), actual.getContent(), "Post content should match");
    }

    @Test
    void shouldThrowNoSuchElementExceptionWhenNoPostsFound() {
        when(postRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> postService.getPostById(1));
    }

    @Test
    void shouldFindPostsByUserId() {
        when(postRepository.findPostsByUserId(any(Integer.class))).thenReturn(List.of(mockPost));

        List<Post> actualPosts = postService.getPostsByUserId(1);
        User actualUser = actualPosts.get(0).getUser();

        assertEquals(1, actualPosts.size(), "Should be a list of 1 post");
        assertEquals("mockuser", actualUser.getUsername());
        verify(postRepository).findPostsByUserId(1);
    }

    @Test
    void shouldSavePost() {
        when(postRepository.save(any(Post.class))).thenReturn(mockPost);

        postService.savePost(mockPost);

        verify(postRepository).save(any(Post.class));
    }
}
