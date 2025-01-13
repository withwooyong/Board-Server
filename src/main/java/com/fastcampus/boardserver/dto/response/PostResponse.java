package com.fastcampus.boardserver.dto.response;

import com.fastcampus.boardserver.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private List<PostDTO> postDTO;
}
