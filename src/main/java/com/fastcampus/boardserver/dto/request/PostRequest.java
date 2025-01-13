package com.fastcampus.boardserver.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {

    private String name;
    private String contents;
    private int views;
    private int categoryId;
    private int userId;
    private int fileId;
    private Date updateTime;
}
