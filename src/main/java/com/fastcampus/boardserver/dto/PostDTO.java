package com.fastcampus.boardserver.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    @Positive
    private int id;
    private String name;

    @Builder.Default
    private boolean isAdmin = false;
    private String contents;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    private int views;
    private int categoryId;

    @Positive // TODO Ted userId > 0
    private int userId;
    private int fileId;

    private LocalDateTime updatedAt;
}
