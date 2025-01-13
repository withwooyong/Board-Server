package com.fastcampus.boardserver.dto;

import com.fastcampus.boardserver.enums.SortStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
//    public enum SortStatus {
//        CATEGORIES, NEWEST, OLDEST, HIGHPRICE, LOWPRICE, GRADE
//    }

    @NotBlank
    private int id;
    private String name;
    private SortStatus sortStatus;
    private int searchCount;
    private int pagingStartOffset;

}
