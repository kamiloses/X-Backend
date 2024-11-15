package com.kamiloses.postservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@Builder
public class PostEntity {
    @Id
    private String id;

    private String userId;

    private String content;

    private LocalDateTime createdAt;

    private int likeCount = 0;

    private int retweetCount = 0;

    private int commentsCount = 0;

    private boolean isDeleted = false;


}