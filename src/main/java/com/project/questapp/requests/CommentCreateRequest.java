package com.project.questapp.requests;

import lombok.Data;

@Data
public class CommentCreateRequest {

    Long postId;
    Long userId;
    String text;
}
