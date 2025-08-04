package com.project.questapp.services;

import com.project.questapp.entities.Like;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.exception.LikeNotFoundException;
import com.project.questapp.repos.LikeRepository;
import com.project.questapp.requests.LikeCreateRequest;
import com.project.questapp.responses.LikeResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {

    private LikeRepository likeRepository;
    private PostService postService;
    private UserService userService;

    public LikeService(LikeRepository likeRepository, PostService postService, UserService userService) {
        this.likeRepository = likeRepository;
        this.postService = postService;
        this.userService = userService;
    }

    public List<LikeResponse> getAllLikes(Optional<Long> postId, Optional<Long> userId) {

        List<Like> list;

        if (postId.isPresent() && userId.isPresent()){
             list = likeRepository.findByPostIdAndUserId(postId.get(), userId.get());
        }
        else if (postId.isPresent()){
            list =  likeRepository.findByPostId(postId.get());
        }
        else if (userId.isPresent()){
            list =  likeRepository.findByUserId(userId.get());
        }
        else {
            list = likeRepository.findAll();
        }
        return  list.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
    }

    public Like createLike(LikeCreateRequest newLike) {

        Post post = postService.getPost(newLike.getPostId());
        if (post == null)
            return null;

        User user = userService.getUser(newLike.getUserId());
        if (user == null)
            return null;

        Like like = new Like();
        like.setId(newLike.getId());
        like.setUser(user);
        like.setPost(post);
        return likeRepository.save(like);
    }

    public void deleteLike(Long likeId) {

        Optional<Like> like = likeRepository.findById(likeId);

        if (like.isPresent()){
        likeRepository.deleteById(likeId);
        }
        else{
            throw new LikeNotFoundException();
        }
    }
}
