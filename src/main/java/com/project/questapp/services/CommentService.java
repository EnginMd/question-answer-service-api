package com.project.questapp.services;

import com.project.questapp.entities.Comment;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.exception.CommentNotFoundException;
import com.project.questapp.repos.CommentRepository;
import com.project.questapp.requests.CommentCreateRequest;
import com.project.questapp.requests.CommentUpdateRequest;
import com.project.questapp.responses.CommentResponse;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.tokens.CommentToken;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private PostService postService;
    private UserService userService;

    public CommentService(CommentRepository commentRepository, PostService postService, UserService userService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.userService = userService;
    }

    public List<CommentResponse> getAllComments(Optional<Long> postId, Optional<Long> userId) {

        List<Comment> comments;

        if (postId.isPresent() && userId.isPresent()){
            comments = commentRepository.findByPostIdAndUserId(postId.get(), userId.get());
        }
        else if (postId.isPresent()){
            comments =  commentRepository.findByPostId(postId.get());
        }
        else if (userId.isPresent()){
            comments =  commentRepository.findByUserId(userId.get());
        }
        else{
            comments =  commentRepository.findAll();
        }

        return comments.stream().map(comment -> new CommentResponse(comment)).collect(Collectors.toList());
    }

    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment createComment(CommentCreateRequest newComment) {

        Post post = postService.getPost(newComment.getPostId());
        if (post == null)
            return null;

        User user = userService.getUser(newComment.getUserId());
        if (user == null){
            return  null;
        }

        Comment comment = new Comment();
        comment.setText(newComment.getText());
        comment.setPost(post);
        comment.setUser(user);
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long commentId, CommentUpdateRequest newComment) {

        Comment comment = getComment(commentId);
        if (comment == null)
            return null;

        comment.setText(newComment.getText());
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {

        Comment comment = getComment(commentId);
        if (comment == null) {
            throw new CommentNotFoundException();
        }
        else {
            commentRepository.deleteById(commentId);
        }
    }
}
