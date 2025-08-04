package com.project.questapp.controllers;

import com.project.questapp.entities.Like;
import com.project.questapp.exception.CommentNotFoundException;
import com.project.questapp.exception.LikeNotFoundException;
import com.project.questapp.requests.LikeCreateRequest;
import com.project.questapp.responses.LikeResponse;
import com.project.questapp.services.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public List<LikeResponse> getAllLikes(@RequestParam Optional<Long> postId, @RequestParam Optional<Long> userId){
        return likeService.getAllLikes(postId, userId);
    }

    @PostMapping
    public Like createLike(@RequestBody LikeCreateRequest newLike){
        return likeService.createLike(newLike);
    }

    @DeleteMapping("/{likeId}")
    public void deleteLike(@PathVariable Long likeId){
        likeService.deleteLike(likeId);
    }

    @ExceptionHandler(LikeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    private void handleAuthenticationFailed(final Exception e, final HttpServletRequest request,
                                            Writer writer) throws IOException {
        writer.write("Like not found.");
    }

}
