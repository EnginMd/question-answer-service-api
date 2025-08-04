package com.project.questapp.controllers;

import com.project.questapp.entities.Post;
import com.project.questapp.exception.CommentNotFoundException;
import com.project.questapp.exception.PostNotFoundException;
import com.project.questapp.requests.PostCreateRequest;
import com.project.questapp.requests.PostUpdateRequest;
import com.project.questapp.responses.PostResponse;
import com.project.questapp.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostResponse> getAllPosts(@RequestParam Optional<Long> userId){
        return postService.getAllPosts(userId);
    }

    @GetMapping("/{postId}")
    public Post getPost(@PathVariable Long postId){

        Post post = postService.getPost(postId);
        if (post == null){
            throw new PostNotFoundException();
        }
        else{
            return post;
        }
    }

    @PostMapping
    public Post createPost(@RequestBody PostCreateRequest newPost){
        return postService.createPost(newPost);
    }

    @PutMapping("/{postId}")
    public Post updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest newPost){
        return postService.updatePost(postId, newPost);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    private void handleAuthenticationFailed(final Exception e, final HttpServletRequest request,
                                            Writer writer) throws IOException {
        writer.write("Post not found.");
    }

}
