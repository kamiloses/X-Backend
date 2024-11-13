package com.kamiloses.commentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentEntity {

    @Id
    private String id;
    private String content;
    private Date createdAt;
    private String userId;
    private String postId;
    private String parentCommentId;
    private Integer numberOfComments;
    private Integer numberOfLikes;
    private Integer numberOfReplies;
}