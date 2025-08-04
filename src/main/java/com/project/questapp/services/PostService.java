package com.project.questapp.services;

import com.project.questapp.entities.Like;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.exception.PostNotFoundException;
import com.project.questapp.repos.LikeRepository;
import com.project.questapp.repos.PostRepository;
import com.project.questapp.requests.PostCreateRequest;
import com.project.questapp.requests.PostUpdateRequest;
import com.project.questapp.responses.LikeResponse;
import com.project.questapp.responses.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private PostRepository postRepository;
    private LikeService likeService;
    private UserService userService;

    public PostService(PostRepository postRepository, UserService userService, @Lazy LikeService likeService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.likeService = likeService;
    }

    public List<PostResponse> getAllPosts(Optional<Long> userId) {

        List<Post> postList;

        if (userId.isPresent()){
            postList = postRepository.findByUserId(userId.get());
        }
        else{
            postList = postRepository.findAll();
        }
        return postList.stream().map(p -> {
            List<LikeResponse> likes = likeService.getAllLikes(Optional.of(p.getId()), Optional.ofNullable(null));
            return new PostResponse(p, likes);
        }).collect(Collectors.toList());
    }
    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public Post createPost(PostCreateRequest newPost) {

        User user = userService.getUser(newPost.getUserId());
        if (user == null){
            return null;
        }

        Post post = new Post();
        post.setTitle((newPost.getTitle()));
        post.setText(newPost.getText());
        post.setUser(user);
        post.setId(newPost.getId());
        return postRepository.save(post);
    }

    public Post updatePost(Long postId, PostUpdateRequest newPost) {

        Optional<Post> post = postRepository.findById(postId);

        if (post.isPresent()){
            post.get().setTitle(newPost.getTitle());
            post.get().setText(newPost.getText());
            return postRepository.save(post.get());
        }
        else{
            throw new PostNotFoundException();
        }
    }

    public void deletePost(Long postId) {

        Post post = getPost(postId);

        if (post == null){
            throw new PostNotFoundException();
        }
        else {
            postRepository.deleteById(postId);
        }


    }
}
