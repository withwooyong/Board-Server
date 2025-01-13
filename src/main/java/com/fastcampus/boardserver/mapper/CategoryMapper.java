package com.fastcampus.boardserver.mapper;

import com.fastcampus.boardserver.dto.CategoryDTO;

public interface CategoryMapper {
    int register(CategoryDTO productDTO);

    void updateCategory(CategoryDTO categoryDTO);

    void deleteCategory(int categoryId);
}
