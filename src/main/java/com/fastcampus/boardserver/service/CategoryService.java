package com.fastcampus.boardserver.service;


import com.fastcampus.boardserver.dto.CategoryDTO;
import com.fastcampus.boardserver.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryMapper categoryMapper;

    @Transactional
    public void register(CategoryDTO categoryDTO) {
        categoryMapper.register(categoryDTO);
    }

    @Transactional
    public void update(CategoryDTO categoryDTO) {
        categoryMapper.updateCategory(categoryDTO);
    }

    @Transactional
    public void delete(int categoryId) {
        categoryMapper.deleteCategory(categoryId);

    }
}
